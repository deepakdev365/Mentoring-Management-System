import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-mentor-dashboard',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './mentor-dashboard.html',
  styleUrls: ['./mentor-dashboard.css']
})
export class MentorDashboard implements OnInit {

  mentorName: string = '';

  constructor(private router: Router) {}

  ngOnInit() {
    this.mentorName = localStorage.getItem('mentorName') || 'Mentor';

    const email = localStorage.getItem('mentorEmail');

  if (!email) {
    this.router.navigate(['/login']); // 🔥 block access
    return;
  }

  this.mentorName = localStorage.getItem('mentorName') || 'Mentor';
  }

  logout() {
  localStorage.clear(); // 🔥 remove all stored data
  this.router.navigate(['/']); // redirect to login page
}

goToProfile() {
  this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
    this.router.navigate(['/mentor/profile']);
  });
}
}
