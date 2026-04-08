import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MentorService } from '../../services/mentor.service';
import { PaymentService } from '../../services/payment.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css',
})

export class AdminDashboardComponent {
   showUpload = false;
  showPaymentUpload = false;

  selectedBacklogFile!: File;
  selectedPaymentFile!: File;

  message = '';
  isError = false;

  paymentMessage = '';
  isPaymentError = false;

  isUploading = false;
  isPaymentUploading = false;


  constructor(private router: Router,
    private mentorService : MentorService,
    private paymentService : PaymentService
  ) {}


goStudents(){
  this.router.navigate(['/admin/students']);
}

goMentor(){
  this.router.navigate(['/admin/mentor']);

}


goAssign(){
  this.router.navigate(['/admin/assign-mentees']);
}

  goToAnnouncement(){
    this.router.navigate(['/admin/make-announcement']);
  }

  goUploadMarks() {
    this.router.navigate(['/admin/upload-marks']);
  }

  goUploadPayments() {
    this.router.navigate(['/admin/upload-payments']);
  }

  goUploadAttendance() {
    this.router.navigate(['/admin/upload-attendance']);
  }

  goUploadSubjectRegistration() {
    this.router.navigate(['/admin/upload-subject-registration']);
  }

}
