import { Component, OnInit } from '@angular/core';
import { Complaint } from '../../../services/complaint'; // Import Complaint
import { ComplaintModel } from '../../../models/complaint.model'; 
import { DatePipe } from '@angular/common';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-view-complaint-component',
  imports: [CommonModule, DatePipe],
  templateUrl: './view-complaints-component.html',
  styleUrls: ['./view-complaints-component.css']
})
export class ViewComplaintComponent implements OnInit {
  complaints: ComplaintModel[] = [];
  mentorEmail = '';
  message = '';

  constructor(private complaintService: Complaint) {} // ✅ Use Complaint (not ComplaintService)

  ngOnInit(): void {
    this.mentorEmail = localStorage.getItem('mentorEmail') || '';
    if (this.mentorEmail) {
      this.loadComplaints();
    } else {
      this.message = 'Mentor not logged in.';
    }
  }

  loadComplaints() {
    this.complaintService.getComplaintsByMentor(this.mentorEmail).subscribe({
      next: (data: ComplaintModel[]) => {
        this.complaints = data;
        if (this.complaints.length === 0) {
          this.message = 'No complaints found.';
        }
      },
      error: (error: any) => {
        this.message = 'Error loading complaints.';
      }
    });
  }
}