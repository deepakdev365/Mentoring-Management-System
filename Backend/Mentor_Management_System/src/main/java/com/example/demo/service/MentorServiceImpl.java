package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.example.demo.model.Mentor;
import com.example.demo.repository.MentorRepository;


@Service
public class MentorServiceImpl implements MentorService {

    private static final String[] REQUIRED_HEADERS = {
            "email", "password", "fullName", "department", "designation", "phoneNo",
            "localAddress", "permanentAddress", "city", "state", "zipCode", "campus"
    };

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Set<String> VALID_CAMPUSES = Set.of(
            "parakhemundi", "bhubaneswar", "rayagada", "balangir", "balasore", "chatrapur"
    );

    private final MentorRepository mentorRepository;

    public MentorServiceImpl(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    @Override
    public List<String> uploadMentorExcel(MultipartFile file) {
        if (!isExcelFile(file)) {
            return List.of("Please upload a valid Excel file (.xls or .xlsx).");
        }

        List<String> errors = new ArrayList<>();
        List<Mentor> mentors = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        Set<String> emailsInFile = new HashSet<>();
        int processedRows = 0;

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

                processedRows++;
                int excelRow = rowIndex + 1;

                String email = getCellValue(row, headers, formatter, "email").toLowerCase();
                String password = getCellValue(row, headers, formatter, "password");
                String fullName = getCellValue(row, headers, formatter, "fullName");
                String department = getCellValue(row, headers, formatter, "department");
                String designation = getCellValue(row, headers, formatter, "designation");
                String phoneNo = getCellValue(row, headers, formatter, "phoneNo", "phone");
                String localAddress = getCellValue(row, headers, formatter, "localAddress");
                String permanentAddress = getCellValue(row, headers, formatter, "permanentAddress");
                String city = getCellValue(row, headers, formatter, "city");
                String state = getCellValue(row, headers, formatter, "state");
                String zipCode = getCellValue(row, headers, formatter, "zipCode", "zipcode");
                String campus = getCellValue(row, headers, formatter, "campus");

                validateRequired(errors, excelRow, "email", email);
                validateRequired(errors, excelRow, "password", password);
                validateRequired(errors, excelRow, "fullName", fullName);
                validateRequired(errors, excelRow, "department", department);
                validateRequired(errors, excelRow, "designation", designation);
                validateRequired(errors, excelRow, "phoneNo", phoneNo);
                validateRequired(errors, excelRow, "city", city);
                validateRequired(errors, excelRow, "state", state);
                validateRequired(errors, excelRow, "zipCode", zipCode);
                validateRequired(errors, excelRow, "campus", campus);

                if (!email.isBlank() && !EMAIL_PATTERN.matcher(email).matches()) {
                    errors.add("Row " + excelRow + " - Invalid email: " + email);
                }

                if (!phoneNo.isBlank() && !phoneNo.matches("\\d{10}")) {
                    errors.add("Row " + excelRow + " - Phone number must contain exactly 10 digits.");
                }

                if (!zipCode.isBlank() && !zipCode.matches("\\d{6}")) {
                    errors.add("Row " + excelRow + " - Zip code must contain exactly 6 digits.");
                }

                if (!campus.isBlank() && !VALID_CAMPUSES.contains(campus.trim().toLowerCase())) {
                    errors.add("Row " + excelRow + " - Invalid campus: " + campus);
                }

                if (!email.isBlank()) {
                    if (!emailsInFile.add(email)) {
                        errors.add("Row " + excelRow + " - Duplicate email inside Excel: " + email);
                    }

                    if (mentorRepository.findByEmail(email).isPresent()) {
                        errors.add("Row " + excelRow + " - Mentor email already exists: " + email);
                    }
                }

                Mentor mentor = new Mentor();
                mentor.setEmail(email);
                mentor.setPassword(password);
                mentor.setFullName(fullName);
                mentor.setDepartment(department);
                mentor.setDesignation(designation);
                mentor.setPhoneNo(phoneNo);
                mentor.setLocalAddress(localAddress);
                mentor.setPermanentAddress(permanentAddress);
                mentor.setCity(city);
                mentor.setState(state);
                mentor.setZipCode(zipCode);
                mentor.setCampus(campus);
                mentors.add(mentor);
            }
        } catch (Exception e) {
            return List.of("Failed to read mentor Excel file: " + e.getMessage());
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        mentorRepository.saveAll(mentors);
        return List.of();
    }

    @Override
    public Mentor login(String email, String password) {
        return mentorRepository
                .findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    @Override
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
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
