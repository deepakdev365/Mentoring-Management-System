import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-mentor-low-attendance',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterModule],
  templateUrl: './mentor-low-attendance.html'
})
export class MentorLowAttendanceComponent implements OnInit {
  students: any[] = [];
  isLoading = true;
  mentorId = 0;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.mentorId = Number(localStorage.getItem("mentorId"));
    this.http.get<any[]>(`http://localhost:8081/api/dashboard/mentor/${this.mentorId}/low-attendance`).subscribe({
      next: (res) => {
        this.students = res;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to load low attendance', err);
        this.isLoading = false;
      }
    });
  }

  goBack() {
    this.router.navigate(['/mentor/dashboard']);
  }
}
