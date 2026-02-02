package com.example.demo.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.demo.model.Mentor;

public class ExcelHelper {

    public static List<Mentor> excelToMentor(InputStream is) {
        List<Mentor> mentors = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            rows.next(); // skip header row

            while (rows.hasNext()) {
                Row row = rows.next();
                Mentor mentor = new Mentor();

                mentor.setEmail(row.getCell(0).getStringCellValue());
                mentor.setPassword(row.getCell(1).getStringCellValue());
                mentor.setFullName(row.getCell(2).getStringCellValue());
                mentor.setDepartment(row.getCell(3).getStringCellValue());
                mentor.setDesignation(row.getCell(4).getStringCellValue());
                mentor.setPhoneNo(row.getCell(5).getStringCellValue());
                mentor.setLocalAddress(row.getCell(6).getStringCellValue());
                mentor.setPermanentAddress(row.getCell(7).getStringCellValue());
                mentor.setCity(row.getCell(8).getStringCellValue());
                mentor.setState(row.getCell(9).getStringCellValue());
                mentor.setZipCode(row.getCell(10).getStringCellValue());

                mentors.add(mentor);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Excel file");
        }

        return mentors;
    }
}
