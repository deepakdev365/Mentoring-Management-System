import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubjectRegistrationService } from '../../services/subject-registration.service';

@Component({
  selector: 'app-upload-subject-registration',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './upload-subject-registration.component.html',
  styleUrl: './upload-subject-registration.component.css'
})
export class UploadSubjectRegistrationComponent {

  requiredColumns = [
    { name: 'registrationNumber', aliases: '/ rollNo / roll' },
    { name: 'subjectCode', aliases: '/ code' },
    { name: 'subjectName', aliases: '/ name / subject' },
    { name: 'credits', aliases: '/ credit' },
    { name: 'semester', aliases: '/ sem' },
  ];

  selectedFile!: File;
  isUploading = false;
  isError = false;
  message = '';
  errorList: string[] = [];
  uploadSuccess = false;

  constructor(private subjectService: SubjectRegistrationService) {}

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
    formData.append('subject', this.selectedFile); // Note: backend expects "subject" param

    this.isUploading = true;
    this.message = '';
    this.errorList = [];
    this.isError = false;
    this.uploadSuccess = false;

    this.subjectService.uploadSubjectRegistrationExcel(formData).subscribe({
      next: (res: any) => {
        this.message = res || 'Subject registrations updated successfully!';
        this.isError = false;
        this.uploadSuccess = true;
        this.isUploading = false;
        this.selectedFile = null as any;
      },
      error: (err) => {
        this.isUploading = false;
        this.isError = true;
        this.uploadSuccess = false;

        // Specialized handling for "net::ERR_UPLOAD_FILE_CHANGED"
        if (err.status === 0 || (err.message && err.message.includes('Changed'))) {
          this.message = 'The file was modified on your computer after selection. Please re-select the file and try again.';
          this.errorList = [];
          this.selectedFile = null as any;
          return;
        }

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
