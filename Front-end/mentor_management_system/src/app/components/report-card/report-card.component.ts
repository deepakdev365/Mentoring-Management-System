import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-report-card',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './report-card.component.html'
})
export class ReportCardComponent {

  rollNo: string = '';
  marks: any[] = [];

  constructor(private service: StudentService) {}

  search() {
    if (!this.rollNo) {
      alert("Enter Roll No");
      return;
    }

    this.service.getReportByRollNo(this.rollNo)
      .subscribe(data => {
        this.marks = data;
      });
  }

  // ✅ ADD THIS
  getTotal(): number {
    return this.marks.reduce((sum, m) => sum + m.totalMarks, 0);
  }

  // ✅ ADD THIS
  getAverage(): number {
    return this.marks.length
      ? this.getTotal() / this.marks.length
      : 0;
  }
}
