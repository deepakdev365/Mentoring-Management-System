import { Component } from '@angular/core';
import { Student } from '../../../services/student';
import { StudentModel } from '../../../models/student.model'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-add-student-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './add-student-component.html',
  styleUrls: ['./add-student-component.css']
})
export class AddStudentComponent {
  studentModel: StudentModel = {
    fullName: '',
    fatherGuardianName: '',
    email: '',
    dob: '',
    gender: '',
    nationality: '',
    religion: '',
    emergencyContact: '',
    phoneNumber: '',
    localAddress: '',
    permanentAddress: '',
    city: '',
    state: '',
    zipCode: '',
    admissionNumber: '',
    applicationNumber: '',
    feeCategory: '',
    dateOfAdmission: '',
    program: '',
    branch: '',
    semester: '',
    rollNo: '',
    eligibilityNumber: '',
    prnNo: '',
    batch: '',
    department: '',
    password: ''
  };

  message = '';

  constructor(private student: Student) {}

  addStudent() {
    if (!this.studentModel.fullName || !this.studentModel.email || !this.studentModel.password) {
      this.message = 'Please fill in all required fields.';
      return;
    }

    this.student.addStudent(this.studentModel).subscribe({
      next: () => {
        this.message = 'Student added successfully!';
        this.studentModel = {
          fullName: '',
          fatherGuardianName: '',
          email: '',
          dob: '',
          gender: '',
          nationality: '',
          religion: '',
          emergencyContact: '',
          phoneNumber: '',
          localAddress: '',
          permanentAddress: '',
          city: '',
          state: '',
          zipCode: '',
          admissionNumber: '',
          applicationNumber: '',
          feeCategory: '',
          dateOfAdmission: '',
          program: '',
          branch: '',
          semester: '',
          rollNo: '',
          eligibilityNumber: '',
          prnNo: '',
          batch: '',
          department: '',
          password: ''
        };
      },
      error: () => {
        this.message = 'Error adding student. Please try again.';
      }
    });
  }
}