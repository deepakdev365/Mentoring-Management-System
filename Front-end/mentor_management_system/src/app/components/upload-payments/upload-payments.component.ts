import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaymentService } from '../../services/payment.service';

@Component({
  selector: 'app-upload-payments',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './upload-payments.component.html',
  styleUrl: './upload-payments.component.css'
})
export class UploadPaymentsComponent {

  requiredColumns = [
    { name: 'email', aliases: '/ mail' },
    { name: 'studentName', aliases: '/ fullname / name' },
    { name: 'rollNo', aliases: '/ regNo / roll' },
    { name: 'amount', aliases: '/ fee' },
    { name: 'semester', aliases: '/ sem' },
    { name: 'paymentDate', aliases: '/ date' },
    { name: 'status', aliases: '(optional)' },
    { name: 'mode', aliases: '(optional)' },
  ];

  selectedFile!: File;
  isUploading = false;
  isError = false;
  message = '';
  errorList: string[] = [];
  uploadSuccess = false;

  constructor(private paymentService: PaymentService) {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.message = '';
    this.errorList = [];
    this.uploadSuccess = false;
  }

  upload() {
    if (!this.selectedFile) {
      this.message = 'Please select an Excel file (.xlsx or .xls) before uploading.';
      this.isError = true;
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.isUploading = true;
    this.message = '';
    this.errorList = [];
    this.isError = false;
    this.uploadSuccess = false;

    this.paymentService.uploadPaymentExcel(formData).subscribe({
      next: (res: any) => {
        const text = typeof res === 'string' ? res : JSON.stringify(res);
        this.message = text || 'Payments uploaded successfully!';
        this.isError = false;
        this.uploadSuccess = true;
        this.isUploading = false;
      },
      error: (err) => {
        this.isUploading = false;
        this.isError = true;
        this.uploadSuccess = false;

        const rawError: string = err?.error || err?.message || 'Upload failed. Please try again.';

        const cleaned = typeof rawError === 'string'
          ? rawError.replace(/^Upload failed:\s*/i, '').trim()
          : String(rawError);

        const parts = cleaned
          .split(/,(?!\s*\d)|\n/)
          .map((s: string) => s.trim())
          .filter((s: string) => s.length > 0);

        if (parts.length > 1) {
          this.errorList = parts;
          this.message = '';
        } else {
          this.message = cleaned;
          this.errorList = [];
        }
      }
    });
  }
}
