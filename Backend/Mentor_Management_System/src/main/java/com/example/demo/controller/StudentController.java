package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200",
allowedHeaders = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    private static final String[] EXPECTED_HEADERS = {

            "fullname",
            "fatherGuardianName",
            "email",
            "dob",
            "gender",
            "nationality",
            "religion",
            "emergencyContact",
            "phoneNumber",
            "localAddress",
            "permanentAddress",
            "state",
            "city",
            "zipcode",
            "admissionNumber",
            "applicationNumber",
            "feeCategory",
            "dateOfAdmission",
            "program",
            "branch",
            "semester",
            "registrationNumber",
            "eligibilityNumber",
            "prnno",
            "batch",
            "department",
            "password"
    };

    @PostMapping("/upload")
    public String uploadStudents(@RequestParam("file") MultipartFile file) {

        List<String> errors = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        try {

            if (file == null || file.isEmpty()) {
                return "Please upload a file.";
            }

            if (!file.getOriginalFilename().endsWith(".xlsx")) {
                return "Invalid file format. Please upload only .xlsx formatted Excel file.";
            }

            Workbook workbook = new XSSFWorkbook(file.getInputStream());

            for (int j = 0; j < workbook.getNumberOfSheets(); j++) {

                Sheet sheet = workbook.getSheetAt(j);
                

                //Checking if the header row exist or not in the excel sheet.
                Row headerRow = sheet.getRow(0);
                if (headerRow == null) {
                    errors.add("Sheet " + sheet.getSheetName() + " has no header row.");
                    continue;
                }

                //Array list to store the headers of the excel sheet uploaded in lower case.
                List<String> actualHeaders = new ArrayList<>();

                //It is scanning the whole row and converting it each headers to lowercase and adding it into the actualHeaders arraylist using "actualHeaders.add(headerValue.trim().toLowerCase());".
                for (int i = 0; i < headerRow.getLastCellNum(); i++) {

                    String headerValue = getCellValue(headerRow, i);

                    if (headerValue != null) {
                        actualHeaders.add(headerValue.trim().toLowerCase());
                    }
                }

                for (String expected : EXPECTED_HEADERS) {

                    if (!actualHeaders.contains(expected.trim().toLowerCase())) {

                        errors.add("Missing required column: " + expected);
                    }
                }

                if (!errors.isEmpty()) {
                    workbook.close();
                    return "Errors found:\n" + String.join("\n", errors);
                }

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    String email = getCellValue(row, 2);
                    String phone = getCellValue(row, 8);

                    if (email == null || email.isEmpty()) {
                        errors.add("Row " + (i + 1) + " - Email is missing.");
                    }

                    if (phone == null || phone.isEmpty()) {
                        errors.add("Row " + (i + 1) + " - Phone Number is missing.");
                    }
                    
                    
                    
                    
                    
//for email id verification
                    if (email == null || email.isEmpty()) {

                        errors.add("Row " + (i + 1) + " - Email is missing.");

                    } else {

                        if (!email.contains("@")) {

                            errors.add("Row " + (i + 1) + 
                                    " - Email is invalid: missing '@' symbol -> " + email);

                        } else if (email.indexOf("@") != email.lastIndexOf("@")) {

                            errors.add("Row " + (i + 1) + 
                                    " - Email is invalid: multiple '@' symbols -> " + email);

                        } else if (!email.substring(email.indexOf("@")).contains(".")) {

                            errors.add("Row " + (i + 1) + 
                                    " - Email is invalid: missing domain extension (like .com) -> " + email);

                        } else if (email.startsWith("@") || email.endsWith("@")) {

                            errors.add("Row " + (i + 1) + 
                                    " - Email is invalid: '@' cannot be at start or end -> " + email);

                        }
                    }
                    
                    
                    
                    
                   
                    
                    
                    //for phone number verification

                    if (phone != null && !phone.isEmpty() &&
                            !phone.matches("\\d{10}")) {
                        errors.add("Row " + (i + 1) + " - Invalid Phone Number: " + phone);
                    }

                    if (email != null && !email.isEmpty() && phone != null && !phone.isEmpty() && email.matches("^[A-Za-z0-9+_.-]+@(.+)$") && phone.matches("\\d{10}")) {

                        Student student = new Student();

                        student.setFullName(getCellValue(row, 0));
                        student.setFatherGuardianName(getCellValue(row, 1));
                        student.setEmail(email);
                        student.setDob(getCellValue(row, 3));
                        student.setGender(getCellValue(row, 4));
                        student.setNationality(getCellValue(row, 5));
                        student.setReligion(getCellValue(row, 6));
                        student.setEmergencyContact(getCellValue(row, 7));
                        student.setPhoneNumber(phone);
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
                        student.setRegistrationNumber(getCellValue(row, 21));     
                        student.setEligibilityNumber(getCellValue(row, 22));
                        student.setPrnNo(getCellValue(row, 23));
                        student.setBatch(getCellValue(row, 24));
                        student.setDepartment(getCellValue(row, 25));
                        student.setPassword(getCellValue(row, 26));

                        students.add(student);
                    }
                }
            }

            workbook.close();

            if (!errors.isEmpty()) {
                return "Errors found:\n" + String.join("\n", errors);
            }

            studentService.saveAllStudent(students);

            return "Students uploaded successfully: " + students.size();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error while uploading students.";
        }
    }

    private String getCellValue(Row row, int index) {

        DataFormatter formatter = new DataFormatter();

        if (row.getCell(index) == null) {
            return null;
        }

        return formatter.formatCellValue(row.getCell(index)).trim();
    }
    
    
    @GetMapping("/all")
    public List<Student> getAllStudents(){
    	return studentService.getAllStudents();
    }
    
    
    
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String password) {

        try {

            Student student = studentService.login(email, password);

            if(student == null){
                return ResponseEntity
                        .badRequest()
                        .body("Invalid Email or Password");
            }

            return ResponseEntity.ok(student);

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body("Invalid Email or Password");

        }

    }
    
    
    
}