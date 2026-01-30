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
  this.mentorService.login(this.email, this.password).subscribe({
    next: () => {
      this.router.navigate(['/mentor-dashboard']);
    },
    error: () => {
      this.errorMessage = 'Invalid email or password';
    }
  });
}

}
