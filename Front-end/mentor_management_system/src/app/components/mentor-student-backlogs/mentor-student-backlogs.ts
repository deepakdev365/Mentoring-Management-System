import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-mentor-student-backlogs',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterModule],
  templateUrl: './mentor-student-backlogs.html'
})
export class MentorStudentBacklogsComponent implements OnInit {
  backlogs: any[] = [];
  isLoading = true;
  mentorId = 0;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.mentorId = Number(localStorage.getItem("mentorId"));
    this.http.get<any[]>(`http://localhost:8081/api/dashboard/mentor/${this.mentorId}/backlogs`).subscribe({
      next: (res) => {
        this.backlogs = res;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to load backlogs', err);
        this.isLoading = false;
      }
    });
  }

  goBack() {
    this.router.navigate(['/mentor/dashboard']);
  }
}
