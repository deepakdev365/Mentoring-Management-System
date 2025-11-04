import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-write-complaint',
  imports: [CommonModule, FormsModule],
  templateUrl: './write-complaint-component.html',
  styleUrls: ['./write-complaint-component.css']
})
export class WriteComplaintComponent {
  complaintText: string = '';
  message: string = '';

  constructor(private http: HttpClient) {}

  submitComplaint(): void {
    const studentEmail = localStorage.getItem('studentEmail');

    if (!studentEmail) {
      this.message = 'You must be logged in as a student to submit a complaint.';
      return;
    }

    if (!this.complaintText.trim()) {
      this.message = 'Complaint cannot be empty.';
      return;
    }

    // First get the full student data
    this.http.get(`http://localhost:8083/api/students/email/${studentEmail}`).subscribe({
      next: (student: any) => {
        // Create complaint with full student object
        const complaint = {
          title: 'Student Complaint - ' + new Date().toLocaleDateString(),
          description: this.complaintText,
          student: student,  // Full student object
          mentor: student.mentor // Mentor from student's mentor
        };

        // Submit the complaint
        this.http.post('http://localhost:8083/api/complaints/add', complaint)
          .subscribe({
            next: (response: any) => {
              this.message = 'Complaint submitted successfully!';
              this.complaintText = '';
            },
            error: (error) => {
              console.error('Complaint submission error:', error);
              this.message = 'Error submitting complaint. Please try again.';
            }
          });
      },
      error: (error) => {
        console.error('Error fetching student data:', error);
        this.message = 'Error loading your data. Please try again.';
      }
    });
  }
}