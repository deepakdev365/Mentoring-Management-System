import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-student-dashboard',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './student-dashboard.component.html',
  styleUrls: ['./student-dashboard.component.css']
  
})
export class StudentDashboardComponent implements OnInit {

  student: any;
  summary: any = {
    attendancePercentage: 0,
    performancePercentage: 0,
    paymentStatus: 'Loading...',
    enrollmentStatus: 'Active',
    activeCourses: 0
  };

  constructor(private router: Router, private http: HttpClient) {}

  ngOnInit(): void {
    const data = sessionStorage.getItem("student");
    if(data){
      this.student = JSON.parse(data);
      console.log("Student Data:", this.student);
      this.fetchSummary();
    }
  }

  fetchSummary() {
    if (this.student?.registrationNumber) {
      this.http.get(`http://localhost:8081/api/dashboard/student/${this.student.registrationNumber}`)
        .subscribe({
          next: (res: any) => {
            this.summary = res;
          },
          error: (err) => {
            console.error("Error fetching summary", err);
            this.summary.paymentStatus = "Check later";
          }
        });
    }
  }

  // Redirect mentor contact to complaint section
  viewMentor(){
    this.router.navigate(['/student/make-complaint']);
  }

  // navigate to complaint page
  goToComplaint(){
    this.router.navigate(['/student/make-complaint']);
  }

  // navigate to complaints list
  viewMyComplaints(){
    this.router.navigate(['/student/my-complaints']);
  }
  
  goToNotifications(){
    this.router.navigate(['/student/notifications']);
  }

  // navigate to report card
  viewReportCard() {
    this.router.navigate(['/student/report-card']);
  }

  // navigate to payments
  goToPayments() {
    this.router.navigate(['/student/payments']);
  }

  // navigate to subjects
  goToSubjects() {
    this.router.navigate(['/student/subjects']);
  }

  // Check if student is hosteler
  isHosteler(): boolean {
    return this.student?.studentType?.toLowerCase() === 'hosteler';
  }

  // navigate to apply leave
  applyLeave() {
    this.router.navigate(['/student/ask-leave-permission']);
  }

  // navigate to leave history
  viewMyLeaves() {
    this.router.navigate(['/student/leave-permissions']);
  }
}