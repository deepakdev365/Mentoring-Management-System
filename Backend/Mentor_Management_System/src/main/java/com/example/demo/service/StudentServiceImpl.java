package com.example.demo.service;

import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.MentorRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import com.example.demo.model.Mentor;
import com.example.demo.model.Student;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final MentorRepository mentorRepository;

    private static final String[][] STUDENT_FIELDS = {
            { "fullName", "name", "studentName", "full name" },
            { "fatherGuardianName", "father", "guardian", "father name" },
            { "email", "mail", "studentEmail" },
            { "dob", "birthdate", "date of birth" },
            { "gender" },
            { "nationality" },
            { "religion" },
            { "emergencyContact", "emergency" },
            { "phoneNumber", "mobile", "phone" },
            { "localAddress", "address" },
            { "permanentAddress" },
            { "city" },
            { "state" },
            { "zipCode", "zipcode", "zip" },
            { "admissionNumber", "admission no" },
            { "applicationNumber", "application no" },
            { "feeCategory" },
            { "dateOfAdmission" },
            { "program", "course" },
            { "branch" },
            { "semester", "sem" },
            { "registrationNumber", "rollNo", "roll", "regNo" },
            { "eligibilityNumber" },
            { "prnNo", "prnno" },
            { "batch" },
            { "department" },
            { "campus" },
            { "studentType", "student_type" },
            { "password" }
    };

    public StudentServiceImpl(StudentRepository studentRepository,
                              MentorRepository mentorRepository) {
        this.studentRepository = studentRepository;
        this.mentorRepository = mentorRepository;
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void saveAllStudent(List<Student> students) {
        studentRepository.saveAll(students);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student login(String email, String password) {
        return studentRepository
                .findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

    @Override
    public void assignMentor(Long mentorId, List<String> registrationNumbers) {
        Mentor mentor = mentorRepository.findById(mentorId.intValue())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        List<Student> students = studentRepository.findByRegistrationNumberIn(registrationNumbers);

        if (students.isEmpty()) {
            throw new RuntimeException("No students found");
        }

        for (Student student : students) {
            student.setMentor(mentor);
        }

        studentRepository.saveAll(students);
    }

    @Override
    public List<Student> getStudentsByMentorId(Integer mentorId) {
        return studentRepository.findByMentor_Id(mentorId);
    }

    @Override
    public void unassignMentor(String regNo) {
        Student student = studentRepository.findByRegistrationNumber(regNo)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setMentor(null);
        studentRepository.save(student);
    }

    @Override
    public List<Student> getStudentsByMentorEmail(String email) {
        return studentRepository.findByMentor_Id(null); // Placeholder logic
    }

    @Override
    public Student getStudentByRegNo(String regNo) {
        return studentRepository.findByRegistrationNumber(regNo)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public List<String> uploadStudents(MultipartFile file) throws Exception {
        List<String> errors = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) return List.of("Excel sheet has no header row.");

            Map<String, Integer> headers = mapHeaders(headerRow, formatter);
            List<String> missing = findMissing(headers, STUDENT_FIELDS);
            if (!missing.isEmpty()) return List.of("Missing required columns: " + String.join(", ", missing));

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row, formatter)) continue;

                int rowNum = i + 1;
                Student s = new Student();
                
                s.setFullName(getVal(row, headers, formatter, "fullName", "name", "studentName", "full name"));
                s.setFatherGuardianName(getVal(row, headers, formatter, "fatherGuardianName", "father", "guardian", "father name"));
                s.setEmail(getVal(row, headers, formatter, "email", "mail", "studentEmail"));
                s.setDob(getVal(row, headers, formatter, "dob", "birthdate", "date of birth"));
                s.setGender(getVal(row, headers, formatter, "gender"));
                s.setNationality(getVal(row, headers, formatter, "nationality"));
                s.setReligion(getVal(row, headers, formatter, "religion"));
                s.setEmergencyContact(getVal(row, headers, formatter, "emergencyContact", "emergency"));
                s.setPhoneNumber(getVal(row, headers, formatter, "phoneNumber", "mobile", "phone"));
                s.setLocalAddress(getVal(row, headers, formatter, "localAddress", "address"));
                s.setPermanentAddress(getVal(row, headers, formatter, "permanentAddress"));
                s.setCity(getVal(row, headers, formatter, "city"));
                s.setState(getVal(row, headers, formatter, "state"));
                s.setZipCode(getVal(row, headers, formatter, "zipCode", "zipcode", "zip"));
                s.setAdmissionNumber(getVal(row, headers, formatter, "admissionNumber", "admission no"));
                s.setApplicationNumber(getVal(row, headers, formatter, "applicationNumber", "application no"));
                s.setFeeCategory(getVal(row, headers, formatter, "feeCategory"));
                s.setDateOfAdmission(getVal(row, headers, formatter, "dateOfAdmission"));
                s.setProgram(getVal(row, headers, formatter, "program", "course"));
                s.setBranch(getVal(row, headers, formatter, "branch"));
                s.setSemester(getVal(row, headers, formatter, "semester", "sem"));
                s.setRegistrationNumber(getVal(row, headers, formatter, "registrationNumber", "rollNo", "roll", "regNo"));     
                s.setEligibilityNumber(getVal(row, headers, formatter, "eligibilityNumber"));
                s.setPrnNo(getVal(row, headers, formatter, "prnNo", "prnno"));
                s.setBatch(getVal(row, headers, formatter, "batch"));
                s.setDepartment(getVal(row, headers, formatter, "department"));
                s.setCampus(getVal(row, headers, formatter, "campus"));
                s.setStudentType(getVal(row, headers, formatter, "studentType", "student_type"));
                s.setPassword(getVal(row, headers, formatter, "password"));

                if (s.getEmail().isEmpty()) errors.add("Row " + rowNum + " - Email is missing.");
                if (s.getRegistrationNumber().isEmpty()) errors.add("Row " + rowNum + " - Registration Number is missing.");
                
                students.add(s);
            }
        }

        if (errors.isEmpty()) {
            studentRepository.saveAll(students);
        }
        return errors;
    }

    private Map<String, Integer> mapHeaders(Row row, DataFormatter formatter) {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (Cell cell : row) {
            String val = norm(formatter.formatCellValue(cell));
            if (!val.isEmpty()) map.put(val, cell.getColumnIndex());
        }
        return map;
    }

    private List<String> findMissing(Map<String, Integer> h, String[][] fields) {
        List<String> missing = new ArrayList<>();
        for (String[] aliases : fields) {
            boolean found = false;
            for (String alias : aliases) {
                if (h.containsKey(norm(alias))) { found = true; break; }
            }
            if (!found) missing.add(aliases[0]);
        }
        return missing;
    }

    private String getVal(Row row, Map<String, Integer> h, DataFormatter f, String... aliases) {
        for (String alias : aliases) {
            Integer idx = h.get(norm(alias));
            if (idx != null) {
                Cell c = row.getCell(idx);
                return c == null ? "" : f.formatCellValue(c).trim();
            }
        }
        return "";
    }

    private String norm(String s) {
        return s == null ? "" : s.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    private boolean isRowEmpty(Row row, DataFormatter f) {
        if (row == null) return true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell c = row.getCell(i);
            if (c != null && !f.formatCellValue(c).trim().isEmpty()) return false;
        }
        return true;
    }
}
