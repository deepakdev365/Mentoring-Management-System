import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MarksService } from '../../services/marks.service';
import { StudentMark } from '../../models/student-mark';

@Component({
  selector: 'app-report-card',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './report-card.component.html',
  styleUrl: './report-card.component.css'
})
export class ReportCardComponent implements OnInit {
  rollNo = '';
  marksList: StudentMark[] = [];
  errorMessage = '';
  loading = false;

  constructor(private marksService: MarksService) {}

  ngOnInit(): void {
    this.loadAllMarks();
  }

  loadAllMarks(): void {
    this.loading = true;
    this.errorMessage = '';

    this.marksService.getAllMarks().subscribe({
      next: (data) => {
        this.marksList = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to fetch marks.';
        this.loading = false;
      }
    });
  }

  searchByRollNo(): void {
    this.errorMessage = '';

    if (!this.rollNo.trim()) {
      this.loadAllMarks();
      return;
    }

    this.loading = true;

    this.marksService.getMarksByRollNo(this.rollNo.trim()).subscribe({
      next: (data) => {
        this.marksList = data;
        this.loading = false;
        if (data.length === 0) {
          this.errorMessage = 'No marks found for this roll number.';
        }
      },
      error: () => {
        this.errorMessage = 'Search failed.';
        this.loading = false;
      }
    });
  }

  resetData(): void {
    this.rollNo = '';
    this.loadAllMarks();
  }
}
