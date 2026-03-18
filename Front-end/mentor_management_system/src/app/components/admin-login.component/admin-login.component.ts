import { Component, OnInit } from '@angular/core';
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
export class AdminLoginComponent implements OnInit {

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

  ngOnInit() {

    const loggedIn = sessionStorage.getItem('adminLoggedIn');

    if (loggedIn === 'true') {
      this.router.navigate(['/admin-dashboard']);
    }

  }

  login() {

    this.adminService.loginAdmin(this.loginData).subscribe({

      next: (res: string) => {

        this.successMsg = res;
        this.errorMsg = '';

        // store login status
        sessionStorage.setItem('adminLoggedIn', 'true');

        // redirect to dashboard
        this.router.navigate(['/admin/dashboard'], { replaceUrl: true });

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