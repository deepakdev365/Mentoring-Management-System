import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Mentor } from '../../../services/mentor'; // ✅ Import Mentor service
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-mentor-login-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './mentor-login-component.html',
  styleUrls: ['./mentor-login-component.css']
})
export class MentorLoginComponent {
  email = '';
  password = '';
  message = '';

  constructor(private mentorService: Mentor, private router: Router) {} // ✅ Use Mentor service

  login() {
    if (!this.email || !this.password) {
      this.message = 'Please enter email and password';
      return;
    }

    this.mentorService.login(this.email, this.password).subscribe({
      next: (response: string) => { // ✅ Add type
        this.message = response;
        if (response === 'Login successful') {
          localStorage.setItem('mentorEmail', this.email); // ✅ Store email
          this.router.navigate(['/mentor/dashboard']);
        }
      },
      error: (error: any) => { // ✅ Add type
        this.message = 'Invalid email or password';
      }
    });
  }
}