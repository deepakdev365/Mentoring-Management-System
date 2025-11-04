import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Student } from '../../../services/student'; // Service
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-student-login-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './student-login-component.html',
  styleUrls: ['./student-login-component.css']
})
export class StudentLoginComponent {
  email = '';
  password = '';
  message = '';

  constructor(private studentService: Student, private router: Router) {} // ✅ Better name

  login() {
    if (!this.email || !this.password) {
      this.message = 'Please enter email and password';
      return;
    }

    this.studentService.login(this.email, this.password).subscribe({
      next: (response) => {
        this.message = response;
        if (response === 'Login successful') {
          // ✅ STORE EMAIL IN LOCALSTORAGE
          localStorage.setItem('studentEmail', this.email);
          this.router.navigate(['/student/dashboard']);
        }
      },
      error: (error) => {
        console.error('Login error:', error);
        this.message = 'Invalid email or password';
      }
    });
  }
}