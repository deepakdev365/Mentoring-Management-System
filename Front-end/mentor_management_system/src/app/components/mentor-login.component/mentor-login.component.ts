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

  if (!this.email || !this.password) {
    this.errorMessage = "Please enter email and password";
    return;
  }

  this.mentorService.login(
  this.email.trim(),
  this.password.trim()
).subscribe({
  next: (res: any) => {
    console.log(res);

    // ✅ store name for navbar
    localStorage.setItem('mentorName', res.name);

    this.router.navigate(['/mentor-dashboard']); // ✅ better than window.location
  },
  error: (err: any) => {
    this.errorMessage = "Invalid email or password";
  }
});
}
}