package com.example.demo.service;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.StudentMarks;
import com.example.demo.repository.StudentMarksRepository;
import com.example.demo.service.StudentMarksService;

@Service
public class StudentMarksServiceImpl implements StudentMarksService {

    private final StudentMarksRepository repository;

    public StudentMarksServiceImpl(StudentMarksRepository repository) {
        this.repository = repository;
    }

    @Override
    public void uploadExcel(MultipartFile file) throws Exception {

        InputStream is = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rows = sheet.iterator();
        rows.next(); // skip header

        while (rows.hasNext()) {
            Row row = rows.next();

            StudentMarks marks = new StudentMarks();

            String rollNo = getCellValue(row.getCell(0));
            String subjectCode = getCellValue(row.getCell(4));

            int internalMarks = parseInt(row.getCell(5));
            int externalMarks = parseInt(row.getCell(6));

            int total = internalMarks + externalMarks;
            double percentage = total; // assuming total is out of 100

            String grade = calculateGrade(percentage);
            String result = percentage >= 40 ? "PASS" : "FAIL";

            marks.setRollNo(rollNo);
            marks.setSubjectCode(subjectCode);
            marks.setInternalMarks(internalMarks);
            marks.setExternalMarks(externalMarks);
            marks.setTotalMarks(total);
            marks.setPercentage(percentage);
            marks.setGrade(grade);
            marks.setResult(result);

            repository.save(marks);
        }

        workbook.close();
    }

    // ================= Helper Methods =================

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.STRING)
            return cell.getStringCellValue().trim();
        if (cell.getCellType() == CellType.NUMERIC)
            return String.valueOf((int) cell.getNumericCellValue());
        return "";
    }

    private int parseInt(Cell cell) {
        try {
            return Integer.parseInt(getCellValue(cell));
        } catch (Exception e) {
            return 0;
        }
    }

    private String calculateGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 75) return "A";
        if (percentage >= 60) return "B";
        if (percentage >= 40) return "C";
        return "F";
    }
}
