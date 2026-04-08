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

import com.example.demo.model.Student;
import com.example.demo.model.SubjectRegistration;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubjectRegistrationRepository;

@Service
public class SubjectRegistrationServiceImpl implements SubjectRegistrationService {

    private static final String[][] REQUIRED_FIELDS = {
            { "registrationNumber", "rollNo", "roll", "registrationNo" },
            { "subjectCode", "code" },
            { "subjectName", "subject" },
            { "credits", "credit" },
            { "semester", "sem" }
    };

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRegistrationRepository subjectRegistrationRepository;

    @Override
    public List<String> uploadSubjectRegistration(MultipartFile file) throws Exception {
        if (!isExcelFile(file)) {
            return List.of("Please upload a valid Excel file (.xls or .xlsx).");
        }

        List<String> errors = new ArrayList<>();
        List<SubjectRegistration> registrationsToSave = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = openWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null || sheet.getPhysicalNumberOfRows() == 0) {
                return List.of("Excel file is empty.");
            }

            Row headerRow = sheet.getRow(sheet.getFirstRowNum());
            Map<String, Integer> headers = mapHeaders(headerRow, formatter);
            List<String> missingHeaders = findMissingHeaders(headers, REQUIRED_FIELDS);

            if (!missingHeaders.isEmpty()) {
                return List.of("Missing headers: " + String.join(", ", missingHeaders));
            }

            for (int rowIndex = sheet.getFirstRowNum() + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (isRowEmpty(row, formatter)) {
                    continue;
                }

                int excelRow = rowIndex + 1;

                String registrationNumber = getCellValue(row, headers, formatter, "registrationNumber", "rollNo",
                        "roll", "registrationNo");
                String subjectCode = getCellValue(row, headers, formatter, "subjectCode", "code");
                String subjectName = getCellValue(row, headers, formatter, "subjectName", "subject");
                String creditsValue = getCellValue(row, headers, formatter, "credits", "credit");
                String semester = getCellValue(row, headers, formatter, "semester", "sem");

                validateRequired(errors, excelRow, "registrationNumber", registrationNumber);
                validateRequired(errors, excelRow, "subjectCode", subjectCode);
                validateRequired(errors, excelRow, "subjectName", subjectName);
                validateRequired(errors, excelRow, "credits", creditsValue);
                validateRequired(errors, excelRow, "semester", semester);

                Integer credits = parseInteger(creditsValue);
                if (credits == null || credits <= 0) {
                    errors.add("Row " + excelRow + " - Credits must be a positive whole number.");
                    continue;
                }

                Optional<Student> studentOpt = studentRepository.findByRegistrationNumber(registrationNumber);
                if (studentOpt.isEmpty()) {
                    errors.add("Row " + excelRow + " - Student not found for registration number: "
                            + registrationNumber);
                    continue;
                }

                Student student = studentOpt.get();

                if (subjectRegistrationRepository.existsByStudentAndSubjectCodeAndSemester(student, subjectCode,
                        semester)) {
                    errors.add("Row " + excelRow + " - Student " + registrationNumber +
                            " is already registered for subject '" + subjectCode +
                            "' in semester " + semester + ".");
                    continue;
                }

                if (student.getSemester() != null
                        && !student.getSemester().equalsIgnoreCase(semester)) {
                    errors.add("Row " + excelRow + " - Semester mismatch for "
                            + registrationNumber + ". Student current semester is " + student.getSemester()
                            + " but Excel record is for semester " + semester + ".");
                    continue;
                }

                SubjectRegistration registration = new SubjectRegistration();
                registration.setStudent(student);
                registration.setSubjectCode(subjectCode);
                registration.setSubjectName(subjectName);
                registration.setCredits(credits);
                registration.setSemester(semester);
                registrationsToSave.add(registration);
            }
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        subjectRegistrationRepository.saveAll(registrationsToSave);
        return List.of();
    }

    @Override
    public List<SubjectRegistration> getRegistrationsByRegNo(String regNo) {
        return subjectRegistrationRepository.findByStudentRegistrationNumber(regNo);
    }

    private void validateRequired(List<String> errors, int excelRow, String fieldName, String value) {
        if (value == null || value.isBlank()) {
            errors.add("Row " + excelRow + " - Missing required value for " + fieldName + ".");
        }
    }

    private Integer parseInteger(String value) {
        try {
            return value == null || value.isBlank() ? null : Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    // --- Excel helpers ---

    private boolean isExcelFile(MultipartFile file) {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null)
            return false;
        String name = file.getOriginalFilename().toLowerCase();
        return name.endsWith(".xlsx") || name.endsWith(".xls");
    }

    private Workbook openWorkbook(MultipartFile file) throws java.io.IOException {
        return org.apache.poi.ss.usermodel.WorkbookFactory.create(file.getInputStream());
    }

    private Map<String, Integer> mapHeaders(Row headerRow, DataFormatter formatter) {
        Map<String, Integer> headers = new java.util.LinkedHashMap<>();
        if (headerRow == null)
            return headers;
        for (org.apache.poi.ss.usermodel.Cell cell : headerRow) {
            String header = normalizeHeader(formatter.formatCellValue(cell));
            if (!header.isEmpty())
                headers.put(header, cell.getColumnIndex());
        }
        return headers;
    }

    private List<String> findMissingHeaders(Map<String, Integer> headers, String[][] requiredFields) {
        List<String> missing = new ArrayList<>();
        for (String[] fieldAliases : requiredFields) {
            boolean found = false;
            for (String alias : fieldAliases) {
                if (headers.containsKey(normalizeHeader(alias))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                missing.add(fieldAliases[0]);
            }
        }
        return missing;
    }

    private String getCellValue(Row row, Map<String, Integer> headers, DataFormatter formatter, String... aliases) {
        for (String alias : aliases) {
            Integer idx = headers.get(normalizeHeader(alias));
            if (idx != null)
                return getCellAt(row, idx, formatter);
        }
        return "";
    }

    private String getCellAt(Row row, int index, DataFormatter formatter) {
        if (row == null || index < 0)
            return "";
        org.apache.poi.ss.usermodel.Cell cell = row.getCell(index);
        return cell == null ? "" : formatter.formatCellValue(cell).trim();
    }

    private boolean isRowEmpty(Row row, DataFormatter formatter) {
        if (row == null)
            return true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            if (!getCellAt(row, i, formatter).isBlank())
                return false;
        }
        return true;
    }

    private String normalizeHeader(String value) {
        return value == null ? "" : value.toLowerCase().replaceAll("[^a-z0-9]", "").trim();
    }
}
