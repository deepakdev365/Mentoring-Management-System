package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Backlog;
import com.example.demo.model.Student;
import com.example.demo.model.StudentMarks;
import com.example.demo.model.SubjectRegistration;
import com.example.demo.repository.BacklogRepository;
import com.example.demo.repository.StudentMarksRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubjectRegistrationRepository;

@Service
public class StudentMarksServiceImpl implements StudentMarksService {

    private static final String[] REQUIRED_HEADERS = {
            "registrationNumber", "subjectCode", "semester", "grade"
    };

    @Autowired
    private StudentMarksRepository repository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRegistrationRepository subjectRegistrationRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Override
    public List<String> uploadExcel(MultipartFile file) throws Exception {
        if (!isExcelFile(file)) {
            return List.of("Please upload a valid Excel file (.xls or .xlsx).");
        }

        List<String> errors = new ArrayList<>();
        List<StudentMarks> marksToSave = new ArrayList<>();
        List<Backlog> backlogsToSave = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = openWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null || sheet.getPhysicalNumberOfRows() == 0) {
                return List.of("Excel file is empty.");
            }

            Row headerRow = sheet.getRow(sheet.getFirstRowNum());
            Map<String, Integer> headers = mapHeaders(headerRow, formatter);
            List<String> missingHeaders = findMissingHeaders(headers, REQUIRED_HEADERS);

            if (!missingHeaders.isEmpty()) {
                return List.of("Missing headers: " + String.join(", ", missingHeaders));
            }

            for (int rowIndex = sheet.getFirstRowNum() + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (isRowEmpty(row, formatter)) {
                    continue;
                }

                int excelRow = rowIndex + 1;

                String registrationNumber = getCellValue(row, headers, formatter, "registrationNumber", "rollNo", "regNo");
                String subjectCode = getCellValue(row, headers, formatter, "subjectCode", "code");
                String semester = getCellValue(row, headers, formatter, "semester", "sem");
                String grade = getCellValue(row, headers, formatter, "grade").toUpperCase();

                validateRequired(errors, excelRow, "registrationNumber", registrationNumber);
                validateRequired(errors, excelRow, "subjectCode", subjectCode);
                validateRequired(errors, excelRow, "semester", semester);
                validateRequired(errors, excelRow, "grade", grade);

                if (registrationNumber.isBlank() || subjectCode.isBlank() || semester.isBlank() || grade.isBlank()) {
                    continue;
                }

                Optional<Student> studentOpt = studentRepository.findByRegistrationNumber(registrationNumber);
                if (studentOpt.isEmpty()) {
                    errors.add("Row " + excelRow + " - Student not found for reg: " + registrationNumber);
                    continue;
                }

                Student student = studentOpt.get();
                Optional<SubjectRegistration> subRegOpt = subjectRegistrationRepository.findByStudentAndSubjectCodeAndSemester(student, subjectCode, semester);
                
                String subjectName = "Unknown";
                if (subRegOpt.isPresent()) {
                    subjectName = subRegOpt.get().getSubjectName();
                } else {
                    errors.add("Row " + excelRow + " - Student " + registrationNumber + " is not registered for subject " + subjectCode + " in semester " + semester);
                    continue;
                }

                // --- Marks Logic ---
                Optional<StudentMarks> marksOpt = repository.findByRollNoAndSubjectCodeAndSemester(registrationNumber, subjectCode, semester);
                StudentMarks marks = marksOpt.orElse(new StudentMarks());
                
                marks.setRollNo(registrationNumber);
                marks.setStudentName(student.getFullName());
                marks.setEmail(student.getEmail());
                marks.setSemester(semester);
                marks.setBranch(student.getBranch());
                marks.setSubjectCode(subjectCode);
                marks.setGrade(grade);
                // Since this format only has 'grade', we reset numeric marks to 0 or leave them null if DB allows.
                // Based on user request, they focus on the grade for automation and report card.
                marks.setResult(grade.equals("F") || grade.equals("S") ? "FAIL" : "PASS");
                
                marksToSave.add(marks);

                // --- Automated Backlog Logic ---
                if (grade.equalsIgnoreCase("F") || grade.equalsIgnoreCase("S")) {
                    Optional<Backlog> backlogOpt = backlogRepository.findByRollNoAndSubjectCodeAndSemester(registrationNumber, subjectCode, semester);
                    Backlog backlog = backlogOpt.orElse(new Backlog());
                    
                    backlog.setRollNo(registrationNumber);
                    backlog.setStudentName(student.getFullName());
                    backlog.setEmail(student.getEmail());
                    backlog.setSubjectCode(subjectCode);
                    backlog.setSubjectName(subjectName);
                    backlog.setSemester(semester);
                    backlog.setBranch(student.getBranch());
                    backlog.setStatus("PENDING");
                    
                    backlogsToSave.add(backlog);
                } else {
                    // If they passed now but had a backlog before, we could mark it as CLEARED?
                    // For now, let's keep it simple as requested.
                }
            }
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        repository.saveAll(marksToSave);
        backlogRepository.saveAll(backlogsToSave);

        return List.of();
    }

    @Override
    public List<StudentMarks> getAllMarks() {
        return repository.findAll();
    }

    @Override
    public List<StudentMarks> getMarksByRollNo(String rollNo) {
        return repository.findByRollNo(rollNo);
    }

    private void validateRequired(List<String> errors, int excelRow, String fieldName, String value) {
        if (value == null || value.isBlank()) {
            errors.add("Row " + excelRow + " - Missing required value for " + fieldName + ".");
        }
    }

    // --- Excel helpers ---

    private boolean isExcelFile(MultipartFile file) {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) return false;
        String name = file.getOriginalFilename().toLowerCase();
        return name.endsWith(".xlsx") || name.endsWith(".xls");
    }

    private Workbook openWorkbook(MultipartFile file) throws java.io.IOException {
        return org.apache.poi.ss.usermodel.WorkbookFactory.create(file.getInputStream());
    }

    private Map<String, Integer> mapHeaders(Row headerRow, DataFormatter formatter) {
        Map<String, Integer> headers = new java.util.LinkedHashMap<>();
        if (headerRow == null) return headers;
        for (org.apache.poi.ss.usermodel.Cell cell : headerRow) {
            String header = normalizeHeader(formatter.formatCellValue(cell));
            if (!header.isEmpty()) headers.put(header, cell.getColumnIndex());
        }
        return headers;
    }

    private List<String> findMissingHeaders(Map<String, Integer> headers, String... required) {
        List<String> missing = new ArrayList<>();
        for (String h : required) {
            if (!headers.containsKey(normalizeHeader(h))) missing.add(h);
        }
        return missing;
    }

    private String getCellValue(Row row, Map<String, Integer> headers, DataFormatter formatter, String... aliases) {
        for (String alias : aliases) {
            Integer idx = headers.get(normalizeHeader(alias));
            if (idx != null) return getCellAt(row, idx, formatter);
        }
        return "";
    }

    private String getCellAt(Row row, int index, DataFormatter formatter) {
        if (row == null || index < 0) return "";
        org.apache.poi.ss.usermodel.Cell cell = row.getCell(index);
        return cell == null ? "" : formatter.formatCellValue(cell).trim();
    }

    private boolean isRowEmpty(Row row, DataFormatter formatter) {
        if (row == null) return true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            if (!getCellAt(row, i, formatter).isBlank()) return false;
        }
        return true;
    }

    private String normalizeHeader(String value) {
        return value == null ? "" : value.toLowerCase().replaceAll("[^a-z0-9]", "").trim();
    }
}
