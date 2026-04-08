import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { StudentService } from '../../services/student.service';
import { MentorService } from '../../services/mentor.service';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-assign-mentees',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './assign-mentees.component.html',
  styleUrl: './assign-mentees.component.css'
})
export class AssignMenteesComponent implements OnInit {

  mentors: any[] = [];
  students: any[] = [];

  mentorSearch = '';
  studentSearch = '';

  selectedMentorId: any = null;
  selectedStudents: string[] = [];

  successMsg = '';
  errorMsg = '';

  constructor(
    private studentService: StudentService,
    private mentorService: MentorService,
    private adminService: AdminService
  ){}

  ngOnInit() {
    this.studentService.getStudents().subscribe((data: any[]) => {
      this.students = data;
    });

    this.mentorService.getMentors().subscribe((data: any[]) => {
      this.mentors = data;
    });
  }

  get filteredMentors() {
    return this.mentors.filter(m =>
      m.fullName.toLowerCase().includes(this.mentorSearch.toLowerCase()) ||
      (m.department && m.department.toLowerCase().includes(this.mentorSearch.toLowerCase()))
    );
  }

  get filteredStudents() {
    return this.students.filter(s =>
      s.fullName.toLowerCase().includes(this.studentSearch.toLowerCase()) ||
      s.registrationNumber.toLowerCase().includes(this.studentSearch.toLowerCase())
    );
  }

  selectMentor(id:number){
    this.selectedMentorId = id;
  }
  isStudentSelected(regNo: string): boolean {
    return this.selectedStudents.includes(regNo);
  }

  toggleStudentSelection(regNo: string) {
    if (this.isStudentSelected(regNo)) {
      this.selectedStudents = this.selectedStudents.filter(s => s !== regNo);
    } else {
      this.selectedStudents.push(regNo);
    }
  }

  onStudentSelect(regNo: string, event: any) {
    if (event.target.checked) {
      if (!this.selectedStudents.includes(regNo)) {
        this.selectedStudents.push(regNo);
      }
    } else {
      this.selectedStudents = this.selectedStudents.filter(s => s !== regNo);
    }
  }
  

  assign() {

    if (!this.selectedMentorId) {
      alert("Please select a mentor first.");
      return;
    }

    if (this.selectedStudents.length === 0) {
      alert("Please select at least one student.");
      return;
    }

    this.adminService.assignMentees(
      this.selectedMentorId,
      this.selectedStudents
    ).subscribe({
      next: (res) => {
        alert("Assigned successfully!");
        this.selectedStudents = [];
        this.selectedMentorId = null;
      },
      error: (err) => {
        console.error(err);
        alert("Assignment failed.");
      }
    });

  }
}