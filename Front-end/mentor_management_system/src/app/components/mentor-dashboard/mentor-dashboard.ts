import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-mentor-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './mentor-dashboard.html',
  styleUrls: ['./mentor-dashboard.css']
})
export class MentorDashboard implements OnInit {

  totalStudents = 0;
  totalComplaints = 0;
  pendingComplaints = 0;

  mentorId: number = 0;

  constructor(private http: HttpClient) {}

  ngOnInit() {

    this.mentorId = Number(localStorage.getItem("mentorId"));

    console.log("Mentor ID:", this.mentorId);

    this.loadStudents();
    this.loadComplaints();
  }

  loadStudents() {

    this.http.get<any[]>(
      `http://localhost:8081/api/students/mentor/${this.mentorId}`
    ).subscribe({
      next: (res) => {
        console.log("Students:", res);
        this.totalStudents = res.length;
      },
      error: (err) => {
        console.log(err);
      }
    });

  }

  loadComplaints() {

    this.http.get<any[]>(
      `http://localhost:8081/complaint/mentor/${this.mentorId}`
    ).subscribe({
      next: (res) => {

        console.log("Complaints:", res);

        this.totalComplaints = res.length;

        this.pendingComplaints = res.filter(
          c => c.status === 'Pending'
        ).length;

      },
      error: (err) => {
        console.log(err);
      }
    });

  }

}