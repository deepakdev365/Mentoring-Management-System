import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MarksService } from '../../services/marks.service';
import { StudentMark } from '../../models/student-mark';

@Component({
  selector: 'app-marks-upload',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './marks-upload.component.html',
  styleUrl: './marks-upload.component.css'
})
export class MarksUploadComponent implements OnInit {
  marksList: StudentMark[] = [];
  selectedFile!: File;
  uploadMessage = '';
  uploadSuccess = false;

  constructor(
    private marksService: MarksService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadMarks();
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedFile = input.files?.[0] as File;
    this.uploadMessage = '';
  }

  loadMarks(): void {
    this.marksService.getAllMarks().subscribe({
      next: (data) => {
        this.marksList = data;
        this.cdr.detectChanges();
      },
      error: () => {
        this.marksList = [];
        this.cdr.detectChanges();
      }
    });
  }

  uploadFile(): void {
    if (!this.selectedFile) {
      this.uploadSuccess = false;
      this.uploadMessage = 'Please select a file first.';
      this.cdr.detectChanges();
      return;
    }

    this.marksService.uploadMarks(this.selectedFile).subscribe({
      next: (res: string) => {
        this.uploadSuccess = true;
        this.uploadMessage = res;
        this.loadMarks();
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.uploadSuccess = false;
        this.uploadMessage = err?.error || 'Error uploading marks file.';
        this.cdr.detectChanges();
      }
    });
  }
}
