import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { LeavePermissionService } from '../../services/leave-permission.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-mentor-leave-permissions',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mentor-leave-permissions.component.html'
})
export class MentorLeavePermissionsComponent implements OnInit {

  mentorId = 0;
  leaves: any[] = [];
  students: any[] = [];
  enrichedLeaves: any[] = [];
  isLoading = true;

  constructor(
    private http: HttpClient,
    private leaveService: LeavePermissionService,
    private router: Router
  ) {}

  ngOnInit() {
    this.mentorId = Number(localStorage.getItem('mentorId'));
    this.loadData();
  }

  loadData() {
    this.isLoading = true;
    
    if (!this.mentorId) {
      console.warn('No mentor ID found, skipping fetch.');
      this.isLoading = false;
      return;
    }

    // Load students and leaves in parallel for efficiency and reliable loading state clearing
    forkJoin({
      students: this.http.get<any[]>(`http://localhost:8081/api/students/mentor/${this.mentorId}`),
      leaves: this.leaveService.getLeavesByMentorId(this.mentorId)
    }).subscribe({
      next: (result) => {
        this.students = result.students || [];
        this.leaves = result.leaves || [];
        
        this.enrichedLeaves = this.leaves.map(leave => {
          const student = this.students.find(s => s.sl_No === leave.studentId);
          return { ...leave, student };
        }).sort((a, b) => (b.id || 0) - (a.id || 0));

        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to load mentor leave data:', err);
        this.isLoading = false;
      }
    });
  }

  getStatusClass(status: string): string {
    if (!status) return 'bg-secondary text-white';
    switch (status.toLowerCase()) {
      case 'approved': return 'bg-success text-white';
      case 'rejected': return 'bg-danger text-white';
      case 'pending':  return 'bg-warning text-dark';
      default:         return 'bg-secondary text-white';
    }
  }

  viewSlip(leave: any) {
    // Store the enriched leave in sessionStorage so the slip page can read it
    sessionStorage.setItem('leaveSlip', JSON.stringify(leave));
    this.router.navigate(['/mentor/leave-slip', leave.id]);
  }

  goBack() {
    this.router.navigate(['/mentor/dashboard']);
  }
}
