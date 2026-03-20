package com.example.demo.service;

import com.example.demo.model.StudentMarks;
import com.example.demo.repository.StudentMarksRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentMarksServiceImpl implements StudentMarksService {

    @Autowired
    private StudentMarksRepository repository;

    @Override
    public void uploadExcel(MultipartFile file) throws Exception {

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {

            // Skip header row
            if (row.getRowNum() == 0) continue;

            StudentMarks marks = new StudentMarks();

            // Excel mapping
            marks.setRollNo(row.getCell(0).getStringCellValue());
            marks.setSubjectCode(row.getCell(1).getStringCellValue());

            int internal = (int) row.getCell(2).getNumericCellValue();
            int external = (int) row.getCell(3).getNumericCellValue();

            int total = internal + external;

            // Set values
            marks.setInternalMarks(internal);
            marks.setExternalMarks(external);
            marks.setTotalMarks(total);

            // Percentage
            double percentage = (total / 100.0) * 100;
            marks.setPercentage(percentage);

            // Grade Logic
            if (percentage >= 90) marks.setGrade("A+");
            else if (percentage >= 75) marks.setGrade("A");
            else if (percentage >= 60) marks.setGrade("B");
            else if (percentage >= 50) marks.setGrade("C");
            else marks.setGrade("F");

            // Result Logic
            if (percentage >= 40) marks.setResult("PASS");
            else marks.setResult("FAIL");

            repository.save(marks);
        }

        workbook.close();
    }
}