import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MentorService } from '../../services/mentor.service';

@Component({
  selector: 'app-mentor',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mentor.component.html',
  styleUrl: './mentor.component.css'
})
export class MentorComponent implements OnInit {

  mentors: any[] = [];
  selectedFile!: File;
  message = '';
  isError = false;
  isUploading = false;

  constructor(
    private mentorService: MentorService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadMentors();
  }

  loadMentors(){
    this.mentorService.getMentors().subscribe((data:any[]) => {
      this.mentors = data;
      this.cdr.detectChanges();
    });
  }

  onFileSelected(event:any){
    this.selectedFile = event.target.files[0];
  }

  viewProfile(email: string) {
    this.router.navigate(['/admin/mentor/profile', email]);
  }

  uploadFile(){
    if (!this.selectedFile) {
      this.message = 'Please select a file first.';
      this.isError = true;
      return;
    }

    this.isUploading = true;
    this.message = '';
    const formData = new FormData();
    formData.append("file", this.selectedFile);

    this.mentorService.uploadExcel(formData).subscribe({
      next: (res) => {
        this.isUploading = false;
        this.message = 'Excel Uploaded Successfully';
        this.isError = false;
        this.loadMentors(); // reload table
      },
      error: (err) => {
        this.isUploading = false;
        this.isError = true;
        this.message = err.error || 'Failed to upload mentor Excel file.';
        console.error('Upload error:', err);
      }
    });
  }


  logout(){
    sessionStorage.removeItem('adminLoggedIn');
    this.router.navigate(['/']);
  }

}