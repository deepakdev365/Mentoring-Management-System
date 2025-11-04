import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Mentor } from '../../../services/mentor';
import { MentorModel } from '../../../models/mentor.model'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-mentor-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './add-mentor-component.html',
  styleUrls: ['./add-mentor-component.css']
})
export class AddMentorComponent {
  mentorModel: MentorModel = {
    // mentorId: 0,  // Remove - backend will generate this
    fullName: '',
    email: '',      // ✅ REQUIRED
    phoneNumber: '', // ✅ REQUIRED
    department: '',
    designation: '',
    gender: '',
    password: ''
  };

  message = '';
  loading = false;

  constructor(private mentorService: Mentor, private router: Router) {}

  addMentor() {
    // ✅ Only validate the required fields from backend
    if (!this.mentorModel.email || !this.mentorModel.phoneNumber) {
      this.message = 'Email and Phone Number are required fields.';
      return;
    }

    this.loading = true;
    this.message = '';

    console.log('Sending mentor data:', this.mentorModel);

    this.mentorService.registerMentor(this.mentorModel).subscribe({
      next: (response) => {
        this.loading = false;
        console.log('Mentor added successfully:', response);
        this.message = 'Mentor added successfully!';
        
        // Reset form
        this.mentorModel = {
          fullName: '',
          email: '',
          phoneNumber: '',
          department: '',
          designation: '',
          gender: '',
          password: ''
        };
      },
      error: (error) => {
        this.loading = false;
        console.error('Error adding mentor:', error);
        
        if (error.status === 500) {
          // Most likely duplicate email error
          this.message = 'A mentor with this email already exists. Please use a different email.';
        } else if (error.error && error.error.message) {
          this.message = error.error.message;
        } else {
          this.message = 'Error adding mentor. Please try again.';
        }
      }
    });
  }
}