import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { LeavePermissionService } from '../../services/leave-permission.service';

@Component({
  selector: 'app-leave-permissions',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './leave-permissions.component.html'
})
export class LeavePermissionsComponent implements OnInit {

  student: any;
  leaves: any[] = [];
  isLoading = true;

  constructor(
    private leaveService: LeavePermissionService,
    private router: Router
  ) {}

  ngOnInit() {
    const data = sessionStorage.getItem("student");
    if (data) {
      this.student = JSON.parse(data);
      this.fetchLeaves();
    } else {
      this.router.navigate(['/login']);
    }
  }

  fetchLeaves() {
    this.leaveService.getStudentLeaves(this.student.sl_No).subscribe({
      next: (res) => {
        // Sort by id descending so newest is first
        this.leaves = res.sort((a, b) => b.id - a.id);
        this.isLoading = false;
      },
      error: (err) => {
        console.error("Error fetching leaves", err);
        this.isLoading = false;
      }
    });
  }

  getStatusClass(status: string): string {
    if(!status) return 'bg-secondary text-white';
    
    switch (status.toLowerCase()) {
      case 'approved': return 'bg-success text-white';
      case 'rejected': return 'bg-danger text-white';
      case 'pending': return 'bg-warning text-dark';
      default: return 'bg-secondary text-white';
    }
  }

  viewLeave(leave: any) {
    // This will be implemented in a future iteration
    alert('Viewing leave details for Request #' + leave.id + ' (Pending Implementation)');
  }
  
  goBack() {
    this.router.navigate(['/student/dashboard']);
  }
}
