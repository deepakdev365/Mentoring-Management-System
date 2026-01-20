import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-student-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './student-login.component.html'
})
export class StudentLoginComponent {

  loginData = {
    email: '',
    password: ''
  };

  successMsg = '';
  errorMsg = '';

  constructor(private studentService: StudentService) {}

  login() {
    this.studentService.loginStudent(this.loginData).subscribe({
      next: (res: string) => {
        this.successMsg = res;
        this.errorMsg = '';
      },
      error: () => {
        this.errorMsg = 'Invalid email or password';
        this.successMsg = '';
      }
    });
  }
}
