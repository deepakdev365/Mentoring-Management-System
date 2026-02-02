package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/upload")
    public String uploadStudents(@RequestParam("file") MultipartFile file) {

        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            List<Student> students = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                Student student = new Student();
                student.setFullName(row.getCell(0).getStringCellValue());
                student.setFatherGuardianName(row.getCell(1).getStringCellValue());
                student.setEmail(row.getCell(2).getStringCellValue());
                student.setPhoneNumber(row.getCell(3).getStringCellValue());
                student.setRollNo(row.getCell(4).getStringCellValue());
                student.setDepartment(row.getCell(5).getStringCellValue());
                student.setBatch(row.getCell(6).getStringCellValue());

                students.add(student);
            }

            studentService.saveAllStudent(students);
            workbook.close();

            return "Students uploaded successfully";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error while uploading students";
        }
    }
}
