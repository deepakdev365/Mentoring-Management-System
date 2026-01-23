import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MentorService } from '../../services/mentor.service';

@Component({
  selector: 'app-mentor-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mentor-login.component.html'
})
export class MentorLoginComponent {

  mentor = {
    email: '',
    password: ''
  };

  successMsg = '';
  errorMsg = '';

  constructor(private mentorService: MentorService) {}

  login() {
    this.mentorService.login(this.mentor).subscribe({
      next: (res: string) => {
        this.successMsg = res;
        this.errorMsg = '';
      },
      error: () => {
        this.successMsg = '';
        this.errorMsg = 'Invalid email or password';
      }
    });
  }
}
