import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MentorService } from '../../services/mentor.service';
import { PaymentService } from '../../services/payment.service';

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
  paymentMessage = '';

  isUploading = false;
  isPaymentUploading = false;

  constructor(
    private router: Router,
    private mentorService: MentorService,
    private paymentService: PaymentService
  ) {}

  goStudents() {
    this.router.navigate(['/admin/students']);
  }

  goMentor() {
    this.router.navigate(['/mentor']);
  }

  goAssign() {
    this.router.navigate(['/admin/assign-mentees']);
  }

  toggleUpload() {
    this.showUpload = !this.showUpload;
    if (this.showUpload) {
      this.showPaymentUpload = false;
    }
    console.log('showUpload =', this.showUpload);
  }

  togglePaymentUpload() {
    this.showPaymentUpload = !this.showPaymentUpload;
    if (this.showPaymentUpload) {
      this.showUpload = false;
    }
    console.log('showPaymentUpload =', this.showPaymentUpload);
  }

  onBacklogFileSelected(event: any) {
    this.selectedBacklogFile = event.target.files[0];
  }

  onPaymentFileSelected(event: any) {
    this.selectedPaymentFile = event.target.files[0];
  }

  uploadBacklogs() {
    if (!this.selectedBacklogFile) {
      this.message = 'Please select a file';
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedBacklogFile);

    this.isUploading = true;

    this.mentorService.uploadBacklogExcel(formData).subscribe({
      next: (res: string) => {
        this.message = res;
        this.isUploading = false;
      },
      error: (err) => {
        console.error(err);
        this.message = 'Upload failed';
        this.isUploading = false;
      }
    });
  }

  uploadPaymentExcel() {
  if (!this.selectedPaymentFile) {
    this.paymentMessage = 'Please select a payment file';
    return;
  }

  const formData = new FormData();
  formData.append('file', this.selectedPaymentFile);

  this.isPaymentUploading = true;

  this.paymentService.uploadPaymentExcel(formData).subscribe({
    next: (res: string) => {
      this.paymentMessage = res;
      this.isPaymentUploading = false;
    },
    error: (err) => {
      console.error('Payment upload error:', err);
      this.paymentMessage = err?.error || 'Payment upload failed';
      this.isPaymentUploading = false;
    }
  });
}
}