import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landing',
  standalone: true,
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent {
  constructor(private router: Router) {}

  goToStudent() {
    this.router.navigate(['/student-login']);
  }

  goToMentor() {
    this.router.navigate(['/mentor-login']);
  }

  goToAdmin() {
    this.router.navigate(['/admin-login']);
  }
}
