package com.example.demo.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.StudentMarks;
import com.example.demo.repository.StudentMarksRepository;

@Service
public class StudentMarksServiceImpl implements StudentMarksService {

    private final StudentMarksRepository repository;
    private final DataFormatter formatter = new DataFormatter();

    public StudentMarksServiceImpl(StudentMarksRepository repository) {
        this.repository = repository;
    }

    @Override
    public void uploadExcel(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Please upload a valid Excel file.");
        }

        List<StudentMarks> marksToSave = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null || sheet.getPhysicalNumberOfRows() == 0) {
                throw new RuntimeException("Excel file is empty.");
            }

            Row headerRow = sheet.getRow(sheet.getFirstRowNum());
            Map<String, Integer> headers = mapHeaders(headerRow);

            for (int rowIndex = sheet.getFirstRowNum() + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (isRowEmpty(row)) {
                    continue;
                }

                StudentMarks marks = new StudentMarks();
                marks.setStudentName(getCellValue(row, headers, "fullname", "studentname", "name"));
                marks.setEmail(getCellValue(row, headers, "email", "mail"));
                marks.setRollNo(getCellValue(row, headers, "rollno", "roll", "regno", "registrationno"));
                marks.setSemester(getCellValue(row, headers, "semester", "sem"));
                marks.setBranch(getCellValue(row, headers, "branch", "department", "dept"));
                marks.setSemMark(getNumericValue(row, headers, "semmark", "sem mark", "semestermark", "semestermarks"));
                marks.setSubjectCode(getCellValue(row, headers, "subjectcode", "subject", "subjectname", "code"));

                double semMark = defaultNumber(marks.getSemMark());
                marks.setTotalMarks(round(semMark));
                marks.setPercentage(round(semMark));
                marks.setGrade(calculateGrade(semMark));
                marks.setResult(calculateResult(semMark));

                if (!marks.getRollNo().isBlank() || !marks.getStudentName().isBlank()) {
                    marksToSave.add(marks);
                }
            }
        }

        if (marksToSave.isEmpty()) {
            throw new RuntimeException("No valid student marks rows found in the uploaded Excel file.");
        }

        repository.deleteAll();
        repository.saveAll(marksToSave);
    }

    @Override
    public List<StudentMarks> getAllMarks() {
        return repository.findAll();
    }

    @Override
    public List<StudentMarks> getMarksByRollNo(String rollNo) {
        return repository.findByRollNo(rollNo);
    }

    private Map<String, Integer> mapHeaders(Row headerRow) {
        Map<String, Integer> headers = new HashMap<>();
        if (headerRow == null) {
            return headers;
        }
        for (Cell cell : headerRow) {
            String key = normalizeHeader(formatter.formatCellValue(cell));
            if (!key.isEmpty()) {
                headers.put(key, cell.getColumnIndex());
            }
        }
        return headers;
    }

    private String getCellValue(Row row, Map<String, Integer> headers, String... aliases) {
        for (String alias : aliases) {
            Integer index = headers.get(normalizeHeader(alias));
            if (index != null) {
                Cell cell = row.getCell(index);
                return cell == null ? "" : formatter.formatCellValue(cell).trim();
            }
        }
        return "";
    }

    private Double getNumericValue(Row row, Map<String, Integer> headers, String... aliases) {
        try {
            String value = getCellValue(row, headers, aliases);
            if (value == null || value.isBlank()) {
                return 0.0;
            }
            value = value.replace("%", "").replace(",", "").trim();
            return Double.parseDouble(value);
        } catch (Exception ex) {
            return 0.0;
        }
    }

    private String normalizeHeader(String value) {
        return value == null ? "" : value.toLowerCase().replaceAll("[^a-z0-9]", "").trim();
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && !formatter.formatCellValue(cell).trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private double defaultNumber(Double value) {
        return value == null ? 0.0 : value;
    }

    private String calculateGrade(double percentage) {
        if (percentage >= 90) return "A";
        if (percentage >= 75) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        return "F";
    }

    private String calculateResult(double percentage) {
        return percentage >= 50 ? "PASS" : "FAIL";
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
