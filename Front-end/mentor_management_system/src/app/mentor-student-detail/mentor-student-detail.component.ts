import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { StudentService } from '../services/student.service';
import { PaymentService } from '../services/payment.service';
import { LeavePermissionService } from '../services/leave-permission.service';
import { BacklogService } from '../services/backlog.service';

@Component({
  selector: 'app-mentor-student-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, HttpClientModule],
  templateUrl: './mentor-student-detail.component.html',
  styleUrls: ['./mentor-student-detail.component.css']
})
export class MentorStudentDetailComponent implements OnInit {

  regNo: string | null = null;
  student: any = null;
  isLoading = true;

  // Additional data properties
  backlogs: any[] = [];
  payments: any[] = [];
  leaves: any[] = [];
  summary: any = null;
  loadingData = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private studentService: StudentService,
    private paymentService: PaymentService,
    private leaveService: LeavePermissionService,
    private backlogService: BacklogService
  ) {}

  ngOnInit() {
    this.regNo = this.route.snapshot.paramMap.get('regNo');
    if (this.regNo) {
      this.loadStudentDetails();
    }
  }

  loadStudentDetails() {
    this.isLoading = true;
    this.studentService.getStudentByRegNo(this.regNo!).subscribe({
      next: (data) => {
        this.student = data;
        this.isLoading = false;
        this.fetchAdditionalData();
      },
      error: (err) => {
        console.error('Error fetching student details', err);
        this.isLoading = false;
      }
    });
  }

  fetchAdditionalData() {
    this.loadingData = true;
    let completedRequests = 0;
    const totalRequests = 4;

    const checkComplete = () => {
      completedRequests++;
      if (completedRequests === totalRequests) {
        this.loadingData = false;
      }
    };

    // 1. Dashboard Summary (for Attendance)
    this.http.get<any>(`http://localhost:8081/api/dashboard/student/${this.regNo}`).subscribe({
      next: (res) => { this.summary = res; checkComplete(); },
      error: (err) => { console.error('Error fetching summary', err); checkComplete(); }
    });

    // 2. Backlogs
    if (this.student.email) {
      this.backlogService.getBacklogsByEmail(this.student.email).subscribe({
        next: (res) => { this.backlogs = res; checkComplete(); },
        error: (err) => { console.error('Error fetching backlogs', err); checkComplete(); }
      });
    } else { 
      checkComplete(); 
    }

    // 3. Payments
    if (this.student.registrationNumber) {
      this.http.get<any[]>(`http://localhost:8081/payment/roll/${this.student.registrationNumber}`).subscribe({
        next: (res) => {
          this.payments = res;
          checkComplete();
        },
        error: (err) => { console.error('Error fetching payments', err); checkComplete(); }
      });
    } else {
      checkComplete();
    }

    // 4. Leave History
    if (this.student.sl_No) {
      this.leaveService.getStudentLeaves(this.student.sl_No).subscribe({
        next: (res) => {
          // Sort by newest first
          this.leaves = res.sort((a: any, b: any) => b.id - a.id);
          checkComplete();
        },
        error: (err) => { console.error('Error fetching leaves', err); checkComplete(); }
      });
    } else {
      checkComplete();
    }
  }

  getPaymentStatusClass(status: string) {
    switch (status?.toLowerCase()) {
      case 'success': return 'badge bg-success-soft text-success';
      case 'pending': return 'badge bg-warning-soft text-warning';
      case 'failed': return 'badge bg-danger-soft text-danger';
      default: return 'badge bg-light text-muted';
    }
  }

  getLeaveStatusClass(status: string): string {
    if(!status) return 'badge bg-secondary text-white';
    
    switch (status.toLowerCase()) {
      case 'approved': return 'badge bg-success text-white';
      case 'rejected': return 'badge bg-danger text-white';
      case 'pending': return 'badge bg-warning text-dark';
      default: return 'badge bg-secondary text-white';
    }
  }

  goBack() {
    this.router.navigate(['/mentor/students']);
  }
}
