import { Component, OnInit } from '@angular/core';
import { Student } from '../../../services/student'; // Service (without "Service")
import { StudentModel } from '../../../models/student.model'; // Model
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-student-dashboard-component',
  imports: [CommonModule, RouterModule],
  templateUrl: './student-dashboard-component.html',
  styleUrls: ['./student-dashboard-component.css']
})
export class StudentDashboardComponent implements OnInit {
  studentModel: StudentModel | null = null;
  message = '';

  constructor(private studentService: Student) {} // ✅ Better name: studentService

  ngOnInit(): void {
    const email = localStorage.getItem('studentEmail');
    if (email) {
      this.loadStudentData(email);
    } else {
      this.message = 'No student logged in!';
    }
  }

  loadStudentData(email: string): void {
    this.studentService.getStudentByEmail(email).subscribe({
      next: (data) => {
        this.studentModel = data;
      },
      error: (error) => {
        console.error('Error loading student data:', error);
        this.message = 'Unable to fetch student data.';
      }
    });
  }
}