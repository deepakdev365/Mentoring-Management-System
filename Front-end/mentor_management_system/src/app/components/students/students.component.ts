import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StudentService } from '../../services/student.service';
import { Router } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './students.component.html',
  styleUrl: './students.component.css',
})
export class StudentsComponent implements OnInit {

  students: any[] = [];
  searchQuery: string = '';
  selectedFile!: File;
  
  uploadMessage = '';
  uploadErrors: string[] = [];
  isUploading = false;

  get filteredStudents() {
    if (!this.searchQuery) return this.students;
    const query = this.searchQuery.toLowerCase();
    return this.students.filter(student => 
      student.fullName.toLowerCase().includes(query) || 
      student.registrationNumber.toLowerCase().includes(query)
    );
  }

  constructor(
    private studentService: StudentService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents() {
    this.studentService.getStudents().subscribe((data: any[]) => {
      this.students = data;
      this.cdr.detectChanges();
    });
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.uploadMessage = '';
    this.uploadErrors = [];
  }

  uploadFile() {
    if (!this.selectedFile) {
      this.uploadMessage = "Please select a file first.";
      this.uploadErrors = [];
      this.cdr.detectChanges();
      return;
    }

    this.isUploading = true;
    this.uploadMessage = '';
    this.uploadErrors = [];

    const formData = new FormData();
    formData.append("file", this.selectedFile);

    this.studentService.uploadExcel(formData).subscribe({
      next: (res: any) => {
        
        let responseText = '';
        if (typeof res === 'string') {
          responseText = res;
        } else if (res && res.message) {
          responseText = res.message;
        } else if (res && res.error && res.error.text) {
          // If HttpClient tries to parse it as JSON but it's a string
          responseText = res.error.text;
        }

        // Parse backend string format
        if (responseText.startsWith("Errors found:")) {
          this.uploadErrors = responseText.split('\n').filter(line => line.trim() !== '' && line.trim() !== 'Errors found:');
          this.uploadMessage = '';
        } else {
          // Success
          this.uploadMessage = responseText || "Students uploaded successfully";
          this.uploadErrors = [];
          this.loadStudents();
        }

        this.isUploading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Upload error:', err);
        
        // Handle browser/security errors like ERR_UPLOAD_FILE_CHANGED (status 0)
        if (err.status === 0) {
          this.uploadMessage = "Upload blocked: The file was modified or deleted after selection. Please re-select the file.";
          this.uploadErrors = [];
        } else if (err.error && typeof err.error === 'string') {
          // Handle case where HTTP client throws because it expected JSON but got String
          let errText = err.error;
          if (errText.startsWith("Errors found:")) {
            this.uploadErrors = errText.split('\n').filter((line: string) => line.trim() !== '' && line.trim() !== 'Errors found:');
            this.uploadMessage = '';
          } else {
            this.uploadMessage = errText;
          }
        } else {
          this.uploadMessage = "Error communicating with the server. Status: " + err.status;
        }
        
        this.isUploading = false;
        this.cdr.detectChanges();
      }
    });

  }

  viewStudent(regNo: string) {
    this.router.navigate(['/admin/student', regNo]);
  }
}