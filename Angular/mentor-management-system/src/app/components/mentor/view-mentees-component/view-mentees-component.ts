import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Student } from '../../../services/student';
import { StudentModel } from '../../../models/student.model';

@Component({
  selector: 'app-view-component-mentees',
  imports: [CommonModule],
  templateUrl: './view-mentees-component.html',
  styleUrls: ['./view-mentees-component.css']
})
export class ViewMenteesComponent implements OnInit {
  mentorEmail = '';
  students: StudentModel[] = [];
  message = '';

  constructor(private student: Student) {}

  ngOnInit(): void {
    this.mentorEmail = localStorage.getItem('mentorEmail') || '';
    // if (this.mentorEmail) {
    //   this.loadMentees();
    // } else {
    //   this.message = 'Mentor not logged in.';
    // }
  }

  // loadMentees(): void {
  //   this.student.getStudentsByMentorEmail(this.mentorEmail).subscribe({
  //     next: (data: Student[]) => {      // ✅ Explicitly typed here
  //       this.students = data;
  //       this.message = this.students.length === 0 ? 'No mentees assigned.' : '';
  //     },
  //     error: () => {
  //       this.message = 'Error loading mentees.';
  //     }
  //   });
  // }
}