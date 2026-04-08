import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-student-subjects',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './student-subjects.component.html',
  styleUrls: ['./student-subjects.component.css']
})
export class StudentSubjectsComponent implements OnInit {

  subjects: any[] = [];
  student: any;
  loading = true;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const data = sessionStorage.getItem("student");
    if (data) {
      this.student = JSON.parse(data);
      this.fetchSubjects();
    }
  }

  fetchSubjects() {
    if (this.student?.registrationNumber) {
      this.http.get<any[]>(`http://localhost:8081/subject/student/${this.student.registrationNumber}`)
        .subscribe({
          next: (res) => {
            this.subjects = res;
            this.loading = false;
          },
          error: (err) => {
            console.error("Error fetching subjects", err);
            this.loading = false;
          }
        });
    }
  }

  getTotalCredits(): number {
    return this.subjects.reduce((sum, s) => sum + (s.credits || 0), 0);
  }
}
