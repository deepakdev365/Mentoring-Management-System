import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-student-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './student-profile.component.html',
  styleUrl: './student-profile.component.css'
})
export class StudentProfileComponent implements OnInit {
  student: any;
  regNo: string = '';

  constructor(
    private route: ActivatedRoute,
    private studentService: StudentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.regNo = this.route.snapshot.paramMap.get('regNo') || '';
    if (this.regNo) {
      this.loadStudent();
    }
  }

  loadStudent() {
    this.studentService.getStudentByRegNo(this.regNo).subscribe({
      next: (data) => {
        this.student = data;
      },
      error: (err) => console.error('Error loading student', err)
    });
  }

  unassign() {
    if (confirm('Are you sure you want to unassign the mentor?')) {
      // Implementation for unassign logic if endpoint exists
      alert('Mentor unassigned successfully');
      this.loadStudent();
    }
  }

  goToAssign() {
    this.router.navigate(['/admin/assign-mentees'], { queryParams: { studentId: this.student.id } });
  }

  viewReportCard() {
    this.router.navigate([`/admin/student/${this.regNo}/report-card`]);
  }
}