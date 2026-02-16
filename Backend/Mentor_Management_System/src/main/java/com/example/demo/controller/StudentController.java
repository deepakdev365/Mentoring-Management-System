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
            int numberOfSheets = workbook.getNumberOfSheets();
            
            List<Student> students = new ArrayList<>();
            for(int j = 0; j < numberOfSheets; j++) {
            	Sheet sheet = workbook.getSheetAt(j);

               

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    Student student = new Student();
                    student.setFullName(getCellValue(row, 0));
                    
                    student.setFatherGuardianName(getCellValue(row, 1));
                    
                    student.setEmail(getCellValue(row, 2));
                    student.setDob(getCellValue(row, 3));
                    
                    student.setGender(getCellValue(row, 4));
                    student.setNationality(getCellValue(row, 5));
                    
                    student.setReligion(getCellValue(row, 6));
                    student.setEmergencyContact(getCellValue(row, 7));
                    student.setPhoneNumber(getCellValue(row, 8));

                    student.setLocalAddress(getCellValue(row, 9));
                    student.setPermanentAddress(getCellValue(row, 10));
                    
                    student.setCity(getCellValue(row, 11));
                    student.setState(getCellValue(row, 12));
                    student.setZipCode(getCellValue(row, 13));

                    
                    student.setAdmissionNumber(getCellValue(row, 14));
                    student.setApplicationNumber(getCellValue(row, 15));
                    student.setFeeCategory(getCellValue(row, 16));
                    
                    student.setDateOfAdmission(getCellValue(row, 17));
                    student.setProgram(getCellValue(row, 18));
                    student.setBranch(getCellValue(row, 19));
                    student.setSemester(getCellValue(row, 20));
                    student.setRollNo(getCellValue(row, 21));
                    
                    student.setEligibilityNumber(getCellValue(row, 22));
                    student.setPrnNo(getCellValue(row, 23));
                    student.setBatch(getCellValue(row, 24));
                    
                    student.setDepartment(getCellValue(row, 25));
                    
                    student.setPassword(getCellValue(row, 26));

                    
                   
                    if (student.getEmail() == null || student.getPhoneNumber() == null) {
                        continue; 
                    
                    }

                    students.add(student);
                }

                studentService.saveAllStudent(students);
                workbook.close();

               

            }
            return "Students uploaded successfully : " + students.size();
            
            	
            }
             catch (Exception e) {
            e.printStackTrace();
            return "Error while uploading students";
        }
    }

    private String getCellValue(Row row, int index) {
        if (row.getCell(index) == null) {
            return null;
        }
        return row.getCell(index).toString().trim();
    }
}
