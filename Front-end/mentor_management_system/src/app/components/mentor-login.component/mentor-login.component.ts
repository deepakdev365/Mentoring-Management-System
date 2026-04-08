import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MentorService } from '../../services/mentor.service';

@Component({
  selector: 'app-mentor-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mentor-login.component.html',
  styleUrl: './mentor-login.component.css'
})
export class MentorLoginComponent implements OnInit {

  loginData = {
    email: '',
    password: ''
  };

  successMsg = '';
  errorMsg = '';

  constructor(
    private mentorService: MentorService,
    private router: Router
  ) {}

  ngOnInit() {
    const loggedIn = sessionStorage.getItem('mentorLoggedIn');
    if (loggedIn === 'true') {
      this.router.navigate(['/mentor/dashboard']);
    }
  }

  login() {
    console.log("INPUT:", this.loginData.email, this.loginData.password); // 🔥 DEBUG

    if (!this.loginData.email || !this.loginData.password) {
      this.errorMsg = "Please enter email and password";
      return;
    }

    this.mentorService.login(
      this.loginData.email.trim(),
      this.loginData.password.trim()
    ).subscribe({
      next: (res: any) => {
        console.log("Login Response:", res);

        this.successMsg = "Login Successful";
        this.errorMsg = "";

        localStorage.setItem('mentorId', String(res.id));
        localStorage.setItem('mentorEmail', res.email);
        localStorage.setItem('mentorName', res.fullName);
        sessionStorage.setItem('mentorLoggedIn', 'true');

        setTimeout(()=>{
          this.router.navigate(['/mentor/dashboard'], {replaceUrl:true});
        }, 800);
      },
      error: (err: any) => {
        console.log("ERROR:", err); // 🔥 VERY IMPORTANT
        this.successMsg = "";
        this.errorMsg = "Invalid email or password";
      }
    });
  }
}