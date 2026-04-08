import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LeavePermissionService } from '../../services/leave-permission.service';

@Component({
  selector: 'app-ask-leave-permission',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ask-leave-permission.component.html'
})
export class AskLeavePermissionComponent implements OnInit {

  student: any;
  successMsg = '';
  errorMsg = '';
  isSubmitting = false;

  leaveData = {
    reason: '',
    fromDate: '',
    toDate: '',
    hostelNo: '',
    roomNo: ''
  };

  minDate: string = '';

  constructor(
    private leaveService: LeavePermissionService,
    private router: Router
  ) {}

  ngOnInit() {
    const today = new Date();
    this.minDate = today.toISOString().split('T')[0];

    const data = sessionStorage.getItem("student");
    if (data) {
      this.student = JSON.parse(data);
    } else {
      this.router.navigate(['/login']);
    }
  }

  submitLeave() {
    if (!this.leaveData.reason || !this.leaveData.fromDate || !this.leaveData.toDate || !this.leaveData.hostelNo || !this.leaveData.roomNo) {
        this.errorMsg = "Please fill all required fields";
        return;
    }

    this.isSubmitting = true;
    this.errorMsg = '';
    
    // Construct payload matching backend entity
    const payload = {
      studentId: this.student.sl_No,
      studentName: this.student.fullName,
      reason: this.leaveData.reason,
      fromDate: this.leaveData.fromDate,
      toDate: this.leaveData.toDate,
      hostelNo: this.leaveData.hostelNo,
      roomNo: this.leaveData.roomNo
    };

    this.leaveService.applyLeave(payload).subscribe({
      next: (res) => {
        this.successMsg = "Leave permission requested successfully!";
        this.isSubmitting = false;
        setTimeout(() => {
          this.router.navigate(['/student/leave-permissions']);
        }, 1500);
      },
      error: (err) => {
        this.errorMsg = "Failed to submit request. Please try again.";
        this.isSubmitting = false;
        console.error(err);
      }
    });

  }
  
  goBack() {
    this.router.navigate(['/student/dashboard']);
  }
}
