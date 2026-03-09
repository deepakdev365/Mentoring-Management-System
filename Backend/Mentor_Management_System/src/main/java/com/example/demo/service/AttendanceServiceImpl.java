package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Attendance;
import com.example.demo.model.Student;
import com.example.demo.model.SubjectRegistration;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubjectRegistrationRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRegistrationRepository subjectRegistrationRepository;

    @Override
    public List<String> uploadAttendance(MultipartFile file) throws Exception {

        List<String> errors = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {

            if (row.getRowNum() == 0)
                continue;

            int rowNum = row.getRowNum() + 1;

            String rollNo = row.getCell(0).getStringCellValue();
            String subjectCode = row.getCell(1).getStringCellValue();
            int totalClasses = (int) row.getCell(2).getNumericCellValue();
            int presentClasses = (int) row.getCell(3).getNumericCellValue();

            // Find student
            Optional<Student> studentOpt = studentRepository.findByRollNo(rollNo);

            if (studentOpt.isEmpty()) {
                errors.add("Row " + rowNum + " - Student not found: " + rollNo);
                continue;
            }

            Student student = studentOpt.get();

            // Find subject registration
            Optional<SubjectRegistration> srOpt =
                    subjectRegistrationRepository.findByStudentAndSubjectCode(student, subjectCode);

            if (srOpt.isEmpty()) {
                errors.add("Row " + rowNum + " - Subject not registered for student: "
                        + rollNo + " Subject: " + subjectCode);
                continue;
            }

            SubjectRegistration sr = srOpt.get();

            // Check duplicate attendance
            if (attendanceRepository.existsBySubjectRegistration(sr)) {
                errors.add("Row " + rowNum + " - Attendance already uploaded for "
                        + rollNo + " Subject: " + subjectCode);
                continue;
            }

            if (totalClasses <= 0) {
                errors.add("Row " + rowNum + " - Total classes must be greater than 0");
                continue;
            }

            if (presentClasses > totalClasses) {
                errors.add("Row " + rowNum + " - Present classes cannot exceed total classes");
                continue;
            }

            double percentage = (presentClasses * 100.0) / totalClasses;

            Attendance attendance = new Attendance();

            attendance.setSubjectRegistration(sr);
            attendance.setTotalClasses(totalClasses);
            attendance.setPresentClasses(presentClasses);
            attendance.setPercentage(percentage);

            attendanceRepository.save(attendance);
        }

        workbook.close();

        return errors;
    }
}