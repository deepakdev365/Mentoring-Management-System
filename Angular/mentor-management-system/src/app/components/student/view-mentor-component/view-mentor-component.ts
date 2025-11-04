import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';  // ✅ Add this import
import { Student } from '../../../services/student';
import { StudentModel } from '../../../models/student.model'; 

@Component({
  selector: 'app-view-mentor',
  imports: [CommonModule], // ✅ Import CommonModule for *ngIf
  templateUrl: './view-mentor-component.html',
  styleUrl: './view-mentor-component.css'
})
export class ViewMentorComponent implements OnInit {
  mentor: any = null;
  message = '';

  constructor(private student: Student) {}

  ngOnInit(): void {
    const studentEmail = localStorage.getItem('studentEmail');
    if (studentEmail) {
      this.loadMentor(studentEmail);
    } else {
      this.message = 'No student logged in!';
    }
  }

  loadMentor(email: string): void {
    this.student.getStudentByEmail(email).subscribe({
      next: (data: StudentModel) => {
        if (data.mentor) {
          this.mentor = data.mentor;
        } else {
          this.message = 'No mentor assigned yet.';
        }
      },
      error: () => {
        this.message = 'Error fetching mentor details.';
      }
    });
  }
}