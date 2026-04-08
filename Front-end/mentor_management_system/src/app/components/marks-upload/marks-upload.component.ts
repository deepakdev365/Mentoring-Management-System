import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MarksService } from '../../services/marks.service';

@Component({
  selector: 'app-marks-upload',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './marks-upload.component.html',
  styleUrl: './marks-upload.component.css'
})
export class MarksUploadComponent implements OnInit {

  requiredColumns = [
    { name: 'registrationNumber', aliases: '/ rollNo / regNo' },
    { name: 'subjectCode', aliases: '/ code' },
    { name: 'semester', aliases: '/ sem' },
    { name: 'grade', aliases: '/ result / remark' },
  ];

  selectedFile!: File;
  isUploading = false;
  isError = false;
  message = '';
  errorList: string[] = [];
  uploadSuccess = false;
  marksList: any[] = [];

  constructor(private marksService: MarksService) {}

  ngOnInit(): void {
    this.loadMarks();
  }

  loadMarks(): void {
    this.marksService.getAllMarks().subscribe({
      next: (data) => this.marksList = data,
      error: (err) => console.error('Error loading marks', err)
    });
  }

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

    this.isUploading = true;
    this.message = '';
    this.errorList = [];
    this.isError = false;
    this.uploadSuccess = false;

    this.marksService.uploadMarks(this.selectedFile).subscribe({
      next: (res: any) => {
        this.message = 'Marks updated successfully! Automated backlogs processed for F/S grades.';
        this.isError = false;
        this.uploadSuccess = true;
        this.isUploading = false;
        this.selectedFile = null as any;
        this.loadMarks();
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
