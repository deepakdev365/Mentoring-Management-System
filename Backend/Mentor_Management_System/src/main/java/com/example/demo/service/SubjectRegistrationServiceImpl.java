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

import com.example.demo.model.Student;
import com.example.demo.model.SubjectRegistration;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubjectRegistrationRepository;

@Service
public class SubjectRegistrationServiceImpl implements SubjectRegistrationService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRegistrationRepository subjectRegistrationRepository;

    @Override
    public List<String> uploadSubjectRegistration(MultipartFile file) throws Exception {

        List<String> errors = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {

            if (row.getRowNum() == 0)
                continue;

            int rowNum = row.getRowNum() + 1;

            String registrationNumber = row.getCell(0).getStringCellValue();
            String subjectCode = row.getCell(1).getStringCellValue();
            String subjectName = row.getCell(2).getStringCellValue();
            int credits = (int) row.getCell(3).getNumericCellValue();
            String semester = row.getCell(4).getStringCellValue();

            Optional<Student> studentOpt = studentRepository.findByRegistrationNumber(registrationNumber);

            if (studentOpt.isEmpty()) {
                errors.add("Row " + rowNum + " - Student not found for RollNo: " + registrationNumber);
                continue;
            }

            Student student = studentOpt.get();

            // CHECK 1 → Subject duplicate for same student
            boolean alreadyRegistered =
                    subjectRegistrationRepository.existsByStudentAndSubjectCode(student, subjectCode);

            if (alreadyRegistered) {
                errors.add("Row " + rowNum + " - Duplicate subject '" + subjectCode +
                        "' for RollNo: " + registrationNumber);
                continue;
            }

            // CHECK 2 → Semester mismatch
            if (!student.getSemester().equals(semester)) {
                errors.add("Row " + rowNum +
                        " - Semester mismatch for RollNo: " + registrationNumber +
                        " (Student semester: " + student.getSemester() +
                        ", Excel semester: " + semester + ")");
                continue;
            }

            SubjectRegistration sr = new SubjectRegistration();
            sr.setStudent(student);
            sr.setSubjectCode(subjectCode);
            sr.setSubjectName(subjectName);
            sr.setCredits(credits);
            sr.setSemester(semester);

            subjectRegistrationRepository.save(sr);
        }

        workbook.close();

        return errors;
    }
}