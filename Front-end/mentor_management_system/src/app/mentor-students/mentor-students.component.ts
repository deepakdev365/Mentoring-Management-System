import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MentorService } from '../services/mentor.service';

@Component({
  selector: 'app-mentor-students',
  imports: [CommonModule],
  templateUrl: './mentor-students.component.html',
  styleUrl: './mentor-students.component.css',
})
export class MentorStudentsComponent implements OnInit {

  students: any[] = [];

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