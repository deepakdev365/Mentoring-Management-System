import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MarksService } from '../../services/marks.service';
import { StudentService } from '../../services/student.service';
import { SubjectRegistrationService } from '../../services/subject-registration.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-report-card',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './report-card.component.html',
  styleUrl: './report-card.component.css'
})
export class ReportCardComponent implements OnInit {
  regNo: string = '';
  student: any;
  allMarks: any[] = [];
  filteredMarks: any[] = [];
  
  selectedSemester: string = '';
  semesters: string[] = [];
  
  today: Date = new Date();
  window = window; // Make window accessible to template
  
  loading = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private marksService: MarksService,
    private studentService: StudentService,
    private subjectService: SubjectRegistrationService
  ) {}

  ngOnInit(): void {
    // Try to get regNo from route parameter (for Admin view)
    this.regNo = this.route.snapshot.paramMap.get('regNo') || '';

    // If not in route, try to get from logged-in student session (for Student view)
    if (!this.regNo) {
      const data = sessionStorage.getItem('student');
      if (data) {
        const student = JSON.parse(data);
        this.regNo = student.registrationNumber;
      }
    }

    if (this.regNo) {
      this.loadStudentAndMarks();
    } else {
      this.errorMessage = 'No student registration number found. Please log in again.';
    }
  }

  loadStudentAndMarks(): void {
    this.loading = true;
    
    forkJoin({
      student: this.studentService.getStudentByRegNo(this.regNo),
      marks: this.marksService.getMarksByRollNo(this.regNo),
      registrations: this.subjectService.getRegistrationsByRegNo(this.regNo)
    }).subscribe({
      next: (data) => {
        this.student = data.student;
        
        // Enrich marks with subject names and credits from registration data
        this.allMarks = data.marks.map(mark => {
          const reg = data.registrations.find(r => 
            r.subjectCode === mark.subjectCode && 
            r.semester === mark.semester
          );
          return {
            ...mark,
            subjectName: reg ? reg.subjectName : 'N/A',
            credits: reg ? reg.credits : 0
          };
        });
        
        this.semesters = [...new Set(this.allMarks.map(m => m.semester))].sort();
        
        if (this.semesters.length > 0) {
          this.selectedSemester = this.student.semester || this.semesters[this.semesters.length - 1];
          this.filterBySemester();
        }
        
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load report card data.';
        this.loading = false;
        console.error(err);
      }
    });
  }

  filterBySemester(): void {
    if (!this.selectedSemester) {
      this.filteredMarks = this.allMarks;
    } else {
      this.filteredMarks = this.allMarks.filter(m => m.semester === this.selectedSemester);
    }
  }

  getTotalCredits(): number {
    return this.filteredMarks.reduce((acc, m) => acc + (m.credits || 0), 0);
  }
}
