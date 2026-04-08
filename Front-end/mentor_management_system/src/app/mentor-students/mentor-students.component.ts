import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MentorService } from '../services/mentor.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-mentor-students',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './mentor-students.component.html',
  styleUrl: './mentor-students.component.css',
})
export class MentorStudentsComponent implements OnInit {

  students: any[] = [];
  searchQuery: string = '';

  get filteredStudents() {
    if (!this.searchQuery) return this.students;
    const query = this.searchQuery.toLowerCase();
    return this.students.filter(s => 
      s.fullName.toLowerCase().includes(query) || 
      s.registrationNumber.toLowerCase().includes(query)
    );
  }

  constructor(private mentorService: MentorService) {}

 ngOnInit() {
  const mentorId = localStorage.getItem('mentorId');
  console.log("Mentor ID from localStorage:", mentorId);

  if (!mentorId) {
    console.error("mentorId is missing in localStorage");
    return;
  }
  
  this.mentorService.getStudentsByMentorId(Number(mentorId))
    .subscribe({
      next: (data: any[]) => {
        console.log("Students:", data);
        this.students = data;
      },
      error: (err: any) => {
        console.log("Error:", err);
      }
    });
}
}