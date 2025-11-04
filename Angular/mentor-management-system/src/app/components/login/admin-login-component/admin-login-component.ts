import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Admin } from '../../../services/admin'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-login-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-login-component.html',
  styleUrls: ['./admin-login-component.css']
})
export class AdminLoginComponent {
  email = '';
  password = '';
  message = '';

  constructor(private adminService: Admin, private router: Router) {} // ✅ Use AdminService

  login() {
    if (!this.email || !this.password) {
      this.message = 'Please enter email and password';
      return;
    }

    this.adminService.login(this.email, this.password).subscribe({
      next: (response: string) => { // ✅ Add type
        this.message = response;
        if (response === 'Login successful') {
          this.router.navigate(['/admin/dashboard']);
        }
      },
      error: (error: any) => { // ✅ Add type
        this.message = 'Invalid email or password';
      }
    });
  }
}