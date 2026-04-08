import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { LeavePermissionService } from '../../services/leave-permission.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-mentor-leave-slip',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mentor-leave-slip.component.html'
})
export class MentorLeaveSlipComponent implements OnInit {

  leave: any = null;
  student: any = null;
  isLoading = true;
  actionMessage = '';
  actionError = '';
  isProcessing = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private leaveService: LeavePermissionService,
    private http: HttpClient
  ) {}

  ngOnInit() {
    // Prefer sessionStorage cache (faster), fallback to API call
    const cached = sessionStorage.getItem('leaveSlip');
    if (cached) {
      const parsed = JSON.parse(cached);
      this.leave = parsed;
      this.student = parsed.student;
      this.isLoading = false;
    } else {
      // Optimized direct lookup by ID (fallback when cache is empty)
      const id = Number(this.route.snapshot.paramMap.get('id'));
      this.leaveService.getLeaveById(id).subscribe({
        next: (leave) => {
          if (leave) {
            this.leave = leave;
            // Fetch student data for enrichment
            this.http.get<any>(`http://localhost:8081/api/students/${leave.registrationNumber || ''}`).subscribe({
              next: (stu) => { this.student = stu; this.isLoading = false; },
              error: () => { this.isLoading = false; }
            });
          } else {
            this.isLoading = false;
          }
        },
        error: () => { this.isLoading = false; }
      });
    }
  }

  approve() {
    this.isProcessing = true;
    this.leaveService.approveLeave(this.leave.id).subscribe({
      next: (res) => {
        this.leave.status = 'Approved';
        this.actionMessage = '✅ Leave request has been Approved successfully.';
        this.isProcessing = false;
      },
      error: () => {
        this.actionError = 'Failed to approve. Please try again.';
        this.isProcessing = false;
      }
    });
  }

  reject() {
    this.isProcessing = true;
    this.leaveService.rejectLeave(this.leave.id).subscribe({
      next: (res) => {
        this.leave.status = 'Rejected';
        this.actionMessage = '❌ Leave request has been Rejected.';
        this.isProcessing = false;
      },
      error: () => {
        this.actionError = 'Failed to reject. Please try again.';
        this.isProcessing = false;
      }
    });
  }

  getStatusClass(status: string): string {
    if (!status) return 'bg-secondary';
    switch (status.toLowerCase()) {
      case 'approved': return 'bg-success';
      case 'rejected': return 'bg-danger';
      case 'pending':  return 'bg-warning text-dark';
      default:         return 'bg-secondary';
    }
  }

  goBack() {
    sessionStorage.removeItem('leaveSlip');
    this.router.navigate(['/mentor/leave-permissions']);
  }
}
