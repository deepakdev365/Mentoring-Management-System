package com.example.demo.service;

import com.example.demo.model.Backlog;
import com.example.demo.repository.BacklogRepository;


import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BacklogServiceImpl implements BacklogService {



    @Autowired
    private BacklogRepository backlogRepository;

    @Override
    public List<String> uploadExcel(MultipartFile file) throws Exception {
        if (!isExcelFile(file)) {
            return List.of("Please upload a valid Excel file (.xls or .xlsx).");
        }

        List<String> errors = new ArrayList<>();
        List<Backlog> backlogsToSave = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = openWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null || sheet.getPhysicalNumberOfRows() == 0) {
                return List.of("Excel file is empty.");
            }

            Row headerRow = sheet.getRow(sheet.getFirstRowNum());
            Map<String, Integer> headers = mapHeaders(headerRow, formatter);
            List<String> missingHeaders = findMissingRequiredFields(headers);

            if (!missingHeaders.isEmpty()) {
                return List.of("Missing required headers: " + String.join(", ", missingHeaders));
            }

            for (int rowIndex = sheet.getFirstRowNum() + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (isRowEmpty(row, formatter)) {
                    continue;
                }

                int excelRow = rowIndex + 1;

                String studentName = getCellValue(row, headers, formatter, "fullname", "studentName", "name");
                String email = getCellValue(row, headers, formatter, "email", "mail").toLowerCase();
                String rollNo = getCellValue(row, headers, formatter, "rollNo", "roll", "regNo", "registrationNo");
                String subjectCode = getCellValue(row, headers, formatter, "subjectCode", "code");
                String subjectName = getCellValue(row, headers, formatter, "subjectName", "subject");
                String semester = getCellValue(row, headers, formatter, "semester", "sem");
                String branch = getCellValue(row, headers, formatter, "branch", "department", "dept");
                String status = getCellValue(row, headers, formatter, "status");
                if (status.isBlank()) {
                    status = "PENDING";
                }

                validateRequired(errors, excelRow, "fullname", studentName);
                validateRequired(errors, excelRow, "email", email);
                validateRequired(errors, excelRow, "rollNo", rollNo);
                validateRequired(errors, excelRow, "subjectCode", subjectCode);
                validateRequired(errors, excelRow, "subjectName", subjectName);
                validateRequired(errors, excelRow, "semester", semester);

                if (!email.isBlank() && !subjectCode.isBlank()
                        && backlogRepository.existsByEmailIgnoreCaseAndSubjectCodeIgnoreCase(email, subjectCode)) {
                    errors.add("Row " + excelRow + " - Backlog already exists for email '" + email
                            + "' and subject '" + subjectCode + "'.");
                    continue;
                }

                Backlog backlog = new Backlog();
                backlog.setStudentName(studentName);
                backlog.setEmail(email);
                backlog.setRollNo(rollNo);
                backlog.setSubjectCode(subjectCode);
                backlog.setSubjectName(subjectName);
                backlog.setSemester(semester);
                backlog.setBranch(branch);
                backlog.setStatus(status.toUpperCase());
                backlogsToSave.add(backlog);
            }
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        backlogRepository.saveAll(backlogsToSave);
        return List.of();
    }

    @Override
    public List<Backlog> getBacklogsByEmail(String email) {
        return backlogRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public void deleteBacklogById(Long id) {
        backlogRepository.deleteById(id);
    }

    private void validateRequired(List<String> errors, int excelRow, String fieldName, String value) {
        if (value == null || value.isBlank()) {
            errors.add("Row " + excelRow + " - Missing required value for " + fieldName + ".");
        }
    }

    /**
     * Checks required fields using all known aliases (mirrors getCellValue aliases).
     * Returns a list of field names that could not be found under any alias.
     */
    private List<String> findMissingRequiredFields(Map<String, Integer> headers) {
        List<String> missing = new ArrayList<>();
        if (!hasAnyHeader(headers, "fullname", "studentname", "name"))
            missing.add("fullname / studentName / name");
        if (!hasAnyHeader(headers, "email", "mail"))
            missing.add("email");
        if (!hasAnyHeader(headers, "rollno", "roll", "regno", "registrationno"))
            missing.add("rollNo / roll / regNo / registrationNo");
        if (!hasAnyHeader(headers, "subjectcode", "code"))
            missing.add("subjectCode / code");
        if (!hasAnyHeader(headers, "subjectname", "subject"))
            missing.add("subjectName / subject");
        if (!hasAnyHeader(headers, "semester", "sem"))
            missing.add("semester / sem");
        return missing;
    }

    private boolean hasAnyHeader(Map<String, Integer> headers, String... aliases) {
        for (String alias : aliases) {
            if (headers.containsKey(alias)) return true;
        }
        return false;
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
