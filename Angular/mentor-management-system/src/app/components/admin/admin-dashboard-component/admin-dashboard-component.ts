import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-admin-dashboard-component',
  imports: [],
  templateUrl: './admin-dashboard-component.html',
  styleUrl: './admin-dashboard-component.css',
})
export class AdminDashboardComponent {

  constructor(private router: Router) {}

  goToAddMentor() {
    this.router.navigate(['/admin/add-mentor']);
  }

  goToAddStudent() {
    this.router.navigate(['/admin/add-student']);
  }

  goToAssignMentees() {
    this.router.navigate(['/admin/assign-mentees']);
  }
}

