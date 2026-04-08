import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AttendanceService } from '../../services/attendance.service';

@Component({
  selector: 'app-upload-attendance',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './upload-attendance.component.html',
  styleUrl: './upload-attendance.component.css'
})
export class UploadAttendanceComponent {

  requiredColumns = [
    { name: 'registrationNumber', aliases: '/ rollNo / roll' },
    { name: 'subjectCode', aliases: '/ code' },
    { name: 'percentage', aliases: '/ attendancePercentage / %' },
    { name: 'semester', aliases: '/ sem' },
  ];

  selectedFile!: File;
  isUploading = false;
  isError = false;
  message = '';
  errorList: string[] = [];
  uploadSuccess = false;

  constructor(private attendanceService: AttendanceService) {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.message = '';
    this.errorList = [];
    this.uploadSuccess = false;
    this.isError = false;
  }

  upload() {
    if (!this.selectedFile) {
      this.message = 'Please select an Excel file (.xlsx or .xls) before uploading.';
      this.isError = true;
      return;
    }

    const formData = new FormData();
    formData.append('attendance', this.selectedFile); // Note: backend expects "attendance" param

    this.isUploading = true;
    this.message = '';
    this.errorList = [];
    this.isError = false;
    this.uploadSuccess = false;

    this.attendanceService.uploadAttendanceExcel(formData).subscribe({
      next: (res: any) => {
        this.message = res || 'Attendance records updated successfully!';
        this.isError = false;
        this.uploadSuccess = true;
        this.isUploading = false;
        this.selectedFile = null as any;
      },
      error: (err) => {
        this.isUploading = false;
        this.isError = true;
        this.uploadSuccess = false;

        const rawError = err?.error || err?.message || 'Upload failed. Please try again.';
        
        if (Array.isArray(rawError)) {
          this.errorList = rawError;
          this.message = '';
        } else {
          this.message = rawError;
          this.errorList = [];
        }
      }
    });
  }
}
