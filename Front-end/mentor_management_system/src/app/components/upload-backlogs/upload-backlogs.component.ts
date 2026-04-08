import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MentorService } from '../../services/mentor.service';

@Component({
  selector: 'app-upload-backlogs',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './upload-backlogs.component.html',
  styleUrl: './upload-backlogs.component.css'
})
export class UploadBacklogsComponent {

  requiredColumns = [
    { name: 'fullname', aliases: '/ studentName / name' },
    { name: 'email', aliases: '/ mail' },
    { name: 'rollNo', aliases: '/ roll / regNo' },
    { name: 'subjectCode', aliases: '/ code' },
    { name: 'subjectName', aliases: '/ subject' },
    { name: 'semester', aliases: '/ sem' },
    { name: 'branch', aliases: '(optional)' },
    { name: 'status', aliases: '(optional)' },
  ];

  selectedFile!: File;
  isUploading = false;
  isError = false;
  message = '';
  errorList: string[] = [];
  uploadSuccess = false;

  constructor(private mentorService: MentorService) {}

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

    this.mentorService.uploadBacklogExcel(formData).subscribe({
      next: (res: any) => {
        const text = typeof res === 'string' ? res : JSON.stringify(res);
        this.message = text || 'Backlogs uploaded successfully!';
        this.isError = false;
        this.uploadSuccess = true;
        this.isUploading = false;
      },
      error: (err) => {
        this.isUploading = false;
        this.isError = true;
        this.uploadSuccess = false;

        // Extract the raw error body
        const rawError: string = err?.error || err?.message || 'Upload failed. Please try again.';

        // Strip leading "Upload failed: " prefix if present
        const cleaned = typeof rawError === 'string'
          ? rawError.replace(/^Upload failed:\s*/i, '').trim()
          : String(rawError);

        // Split on comma or newline to produce a list
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
