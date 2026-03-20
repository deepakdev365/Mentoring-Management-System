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

  this.mentorService.login(this.email.trim(), this.password.trim()).subscribe({
    next: (res) => {
  console.log("SUCCESS:", res);   
  this.router.navigate(['/mentor-dashboard']);
},
error: (err) => {
  console.log("ERROR:", err);     
  this.errorMessage = 'Invalid email or password';
}
  });
}

}
