package com.example.demo.controller;

import com.example.demo.model.StudentMarks;
import com.example.demo.repository.StudentMarksRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/marks")
public class StudentMarksController {

    @Autowired
    private StudentMarksRepository repository;

    // ✅ Upload Excel
    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file) {

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {

                if (row.getRowNum() == 0) continue;

                StudentMarks marks = new StudentMarks();

                // ✅ Correct column mapping (skip ID column)
                String rollNo = getStringValue(row.getCell(1));       // rollNo
                String subjectCode = getStringValue(row.getCell(2));  // subjectCode

                int internal = (int) getNumericValue(row.getCell(3)); // internalMarks
                int external = (int) getNumericValue(row.getCell(4)); // externalMarks

                int total = internal + external;

                // ✅ Correct percentage
                double percentage = total; // out of 100

                // ✅ Grade logic
                String grade;
                if (percentage >= 90) grade = "A+";
                else if (percentage >= 75) grade = "A";
                else if (percentage >= 60) grade = "B";
                else if (percentage >= 50) grade = "C";
                else grade = "F";

                // ✅ Result logic
                String result = (percentage >= 40) ? "PASS" : "FAIL";

                // ✅ Set values
                marks.setRollNo(rollNo);
                marks.setSubjectCode(subjectCode);
                marks.setInternalMarks(internal);
                marks.setExternalMarks(external);
                marks.setTotalMarks(total);
                marks.setPercentage(percentage);
                marks.setGrade(grade);
                marks.setResult(result);

                repository.save(marks);
            }

            return "Excel uploaded successfully ✅";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // ✅ Fetch by rollNo
    @GetMapping("/{rollNo}")
    public List<StudentMarks> getByRollNo(@PathVariable String rollNo) {
        return repository.findByRollNo(rollNo);
    }

    // ✅ Helper: numeric values
    private double getNumericValue(Cell cell) {
        if (cell == null) return 0;

        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue().trim());
            } catch (Exception e) {
                return 0;
            }
        } else if (cell.getCellType() == CellType.FORMULA) {
            return cell.getNumericCellValue();
        }
        return 0;
    }

    // ✅ Helper: string values
    private String getStringValue(Cell cell) {
        if (cell == null) return "";

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((long) cell.getNumericCellValue());
        } else {
            return cell.toString();
        }
    }
}