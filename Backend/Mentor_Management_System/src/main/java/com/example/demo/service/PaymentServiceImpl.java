package com.example.demo.service;

import com.example.demo.model.Payment;
import com.example.demo.model.Student;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.StudentRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final String[][] REQUIRED_FIELDS = {
            { "rollNo", "roll", "regNo", "registrationNo", "registrationNumber" },
            { "amount", "fee" },
            { "semester", "sem" },
            { "paymentDate", "date" }
    };

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Payment makePayment(Payment payment) {
        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElse(null);
    }

    @Override
    public List<Payment> getPaymentsByStudent(Long studentId) {
        return paymentRepository.findByStudentId(studentId);
    }

    @Override
    public List<Payment> getPaymentsByMentor(Long mentorId) {
        return paymentRepository.findByMentorId(mentorId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentByEmail(String email) {
        Optional<Student> studentOpt = studentRepository.findByEmail(email);
        if (studentOpt.isPresent()) {
            List<Payment> p = paymentRepository.findByStudentId(studentOpt.get().getSl_No());
            return p.isEmpty() ? null : p.get(0);
        }
        return null;
    }

    @Override
    public List<Payment> getPaymentsByRollNo(String rollNo) {
        Optional<Student> studentOpt = studentRepository.findByRegistrationNumber(rollNo);
        if (studentOpt.isPresent()) {
            return paymentRepository.findByStudentId(studentOpt.get().getSl_No());
        }
        return List.of();
    }

    @Override
    public List<String> uploadPayments(MultipartFile file) throws Exception {
        if (!isExcelFile(file)) {
            return List.of("Please upload a valid Excel file (.xls or .xlsx).");
        }

        List<String> errors = new ArrayList<>();
        List<Payment> paymentsToSave = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
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
                if (isRowEmpty(row, formatter))
                    continue;

                int excelRow = rowIndex + 1;

                String rollNo = getCellValue(row, headers, formatter, "rollNo", "roll", "regNo", "registrationNo",
                        "registrationNumber");
                String amountStr = getCellValue(row, headers, formatter, "amount", "fee");
                String semester = getCellValue(row, headers, formatter, "semester", "sem");
                String dateStr = getCellValue(row, headers, formatter, "paymentDate", "date");
                String status = getCellValue(row, headers, formatter, "status");
                String mode = getCellValue(row, headers, formatter, "mode");

                if (rollNo.isEmpty() || amountStr.isEmpty() || semester.isEmpty()) {
                    errors.add("Row " + excelRow + " - Missing required fields (Roll No, Amount, or Semester).");
                    continue;
                }

                Optional<Student> studentOpt = studentRepository.findByRegistrationNumber(rollNo);
                if (studentOpt.isEmpty()) {
                    errors.add("Row " + excelRow + " - Student not found with registration number: " + rollNo);
                    continue;
                }

                Student student = studentOpt.get();
                Payment payment = new Payment();
                payment.setStudentId(student.getSl_No());
                payment.setMentorId(student.getMentor() != null ? Long.valueOf(student.getMentor().getId()) : null);

                try {
                    payment.setAmount(Double.parseDouble(amountStr));
                } catch (NumberFormatException e) {
                    errors.add("Row " + excelRow + " - Invalid amount: " + amountStr);
                    continue;
                }

                payment.setSemester(semester);
                payment.setPaymentStatus(status.isEmpty() ? "SUCCESS" : status.toUpperCase());
                payment.setPaymentMode(mode.isEmpty() ? "CASH" : mode.toUpperCase());

                // Handle date
                LocalDateTime paymentDate = parseDate(row, headers, "paymentDate", "date");
                payment.setPaymentDate(paymentDate != null ? paymentDate : LocalDateTime.now());

                paymentsToSave.add(payment);
            }
        }

        if (!errors.isEmpty())
            return errors;

        paymentRepository.saveAll(paymentsToSave);
        return List.of();
    }

    private boolean isExcelFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        return name != null && (name.endsWith(".xlsx") || name.endsWith(".xls"));
    }

    private Map<String, Integer> mapHeaders(Row headerRow, DataFormatter formatter) {
        Map<String, Integer> headers = new HashMap<>();
        for (Cell cell : headerRow) {
            String val = formatter.formatCellValue(cell).toLowerCase().replaceAll("[^a-z0-9]", "").trim();
            if (!val.isEmpty())
                headers.put(val, cell.getColumnIndex());
        }
        return headers;
    }

    private List<String> findMissingHeaders(Map<String, Integer> headers, String[][] required) {
        List<String> missing = new ArrayList<>();
        for (String[] aliases : required) {
            boolean found = false;
            for (String alias : aliases) {
                if (headers.containsKey(alias.toLowerCase().replaceAll("[^a-z0-9]", ""))) {
                    found = true;
                    break;
                }
            }
            if (!found)
                missing.add(aliases[0]);
        }
        return missing;
    }

    private String getCellValue(Row row, Map<String, Integer> headers, DataFormatter formatter, String... aliases) {
        for (String alias : aliases) {
            Integer idx = headers.get(alias.toLowerCase().replaceAll("[^a-z0-9]", ""));
            if (idx != null) {
                Cell cell = row.getCell(idx);
                return cell == null ? "" : formatter.formatCellValue(cell).trim();
            }
        }
        return "";
    }

    private LocalDateTime parseDate(Row row, Map<String, Integer> headers, String... aliases) {
        for (String alias : aliases) {
            Integer idx = headers.get(alias.toLowerCase().replaceAll("[^a-z0-9]", ""));
            if (idx != null) {
                Cell cell = row.getCell(idx);
                if (cell != null) {
                    if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    }
                    // Try string parsing if needed
                }
            }
        }
        return null;
    }

    private boolean isRowEmpty(Row row, DataFormatter formatter) {
        if (row == null)
            return true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            if (!formatter.formatCellValue(row.getCell(i)).trim().isEmpty())
                return false;
        }
        return true;
    }
}
