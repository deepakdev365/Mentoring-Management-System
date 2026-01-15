import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-login.component.html'
})
export class AdminLoginComponent {

  loginData = {
    email: '',
    password: ''
  };

  successMsg = '';
  errorMsg = '';

  constructor(
    private adminService: AdminService,
    private router: Router
  ) {}

  login() {
    console.log(this.loginData);

    this.adminService.loginAdmin(this.loginData).subscribe({
      next: (res: string) => {
        console.log('Login success:', res);

        this.successMsg = res;     // "Login Successful"
        this.errorMsg = '';

        // ✅ Navigate to Admin Dashboard
        setTimeout(() => {
          this.router.navigate(['/admin-dashboard']);
        }, 800);
      },
      error: (err) => {
        this.successMsg = '';
        if (err.status === 401) {
          this.errorMsg = 'Invalid email or password';
        } else {
          this.errorMsg = 'Server error';
        }
      }
    });
  }
}
