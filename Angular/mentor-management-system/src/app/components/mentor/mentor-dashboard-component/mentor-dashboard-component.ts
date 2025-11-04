import { Component, OnInit } from '@angular/core';
import { Mentor } from '../../../services/mentor';
import { Student } from '../../../services/student';
import { Complaint } from '../../../services/complaint';  // ✅ Import Complaint service
import { StudentModel } from '../../../models/student.model'; 
import { MentorModel } from '../../../models/mentor.model';
import { ComplaintModel } from '../../../models/complaint.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-mentor-dashboard-component',
  imports: [CommonModule],
  templateUrl: './mentor-dashboard-component.html',
  styleUrls: ['./mentor-dashboard-component.css']
})
export class MentorDashboardComponent implements OnInit {
  mentorEmail = '';
  mentor?: MentorModel;
  mentees: StudentModel[] = [];
  complaints: ComplaintModel[] = [];
  message = '';

  constructor(
    private mentorService: Mentor,
    private studentService: Student,
    private complaintService: Complaint // ✅ Use Complaint service
  ) {}

  ngOnInit(): void {
    this.mentorEmail = localStorage.getItem('mentorEmail') || '';
    if (this.mentorEmail) {
      this.loadMentorData();
    } else {
      this.message = 'Please log in as a mentor.';
    }
  }

  loadMentorData() {
    this.mentorService.getMentorByEmail(this.mentorEmail).subscribe({
      next: (mentor: MentorModel) => {
        this.mentor = mentor;
        this.loadMentees();
        this.loadComplaints();
      },
      error: (error: any) => {
        this.message = 'Error loading mentor data.';
      }
    });
  }

  loadMentees() {
    this.studentService.getAllStudents().subscribe({
      next: (students: StudentModel[]) => {
        this.mentees = students.filter(s => s.mentor && s.mentor.email === this.mentorEmail);
      },
      error: (error: any) => {
        this.message = 'Error loading mentees.';
      }
    });
  }

  loadComplaints() {
    this.complaintService.getComplaintsByMentor(this.mentorEmail).subscribe({
      next: (data: ComplaintModel[]) => {
        this.complaints = data;
      },
      error: (error: any) => {
        this.message = 'Error fetching complaints.';
      }
    });
  }
}