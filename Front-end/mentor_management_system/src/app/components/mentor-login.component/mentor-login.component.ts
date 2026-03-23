import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MentorService } from '../../services/mentor.service';

@Component({
  selector: 'app-mentor-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mentor-login.component.html',
})
export class MentorLoginComponent {
  email = '';
  password = '';
  errorMessage = '';

  constructor(
    private mentorService: MentorService,
    private router: Router
  ) {}

login() {

  console.log("INPUT:", this.email, this.password); // 🔥 DEBUG

  if (!this.email || !this.password) {
    this.errorMessage = "Please enter email and password";
    return;
  }

  this.mentorService.login(
    this.email.trim(),
    this.password.trim()
  ).subscribe({
    next: (res: any) => {
      console.log("Login Response:", res);

      localStorage.setItem('mentorId', String(res.id));
      localStorage.setItem('mentorEmail', res.email);
      localStorage.setItem('mentorName', res.fullName);

      this.router.navigate(['/mentor/dashboard']);
    },
    error: (err: any) => {
      console.log("ERROR:", err); // 🔥 VERY IMPORTANT
      this.errorMessage = "Invalid email or password";
    }
  });
}
}