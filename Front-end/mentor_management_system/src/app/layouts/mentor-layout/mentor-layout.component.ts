import { Component, OnInit } from '@angular/core';
import { Router, RouterModule, NavigationEnd } from '@angular/router';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-mentor-layout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './mentor-layout.component.html',
  styleUrl: './mentor-layout.component.css'
})
export class MentorLayoutComponent implements OnInit {

  mentor: any = {
    fullName: 'Mentor',
    initials: 'M'
  };
  
  currentPath: string = 'Dashboard';

  constructor(private router: Router) {}

  ngOnInit() {
    this.loadMentorData();
    this.trackRoute();
  }

  loadMentorData() {
    const email = localStorage.getItem('mentorEmail');
    if (!email) {
      this.router.navigate(['/mentor-login']);
      return;
    }

    const name = localStorage.getItem('mentorName') || 'Faculty Mentor';
    this.mentor = {
      fullName: name,
      initials: this.getInitials(name),
      email: email
    };
  }

  trackRoute() {
    // Set initial path
    this.updateBreadcrumb(this.router.url);

    // Track changes
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      this.updateBreadcrumb(event.urlAfterRedirects);
    });
  }

  updateBreadcrumb(url: string) {
    if (url.includes('dashboard')) this.currentPath = 'Dashboard';
    else if (url.includes('students')) this.currentPath = 'My Students';
    else if (url.includes('complaints')) this.currentPath = 'Complaints';
    else if (url.includes('profile')) this.currentPath = 'My Profile';
    else if (url.includes('student/')) this.currentPath = 'Student Details';
  }

  getInitials(name: string): string {
    return name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0, 2);
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/']);
  }

  goToProfile() {
    this.router.navigate(['/mentor/profile']);
  }
}