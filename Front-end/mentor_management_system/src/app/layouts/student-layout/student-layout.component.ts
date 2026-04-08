import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet, RouterLink, RouterLinkActive, NavigationEnd } from '@angular/router';
import { CommonModule } from '@angular/common';
import { filter, map } from 'rxjs/operators';

@Component({
  selector: 'app-student-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './student-layout.component.html',
  styleUrl: './student-layout.component.css'
})
export class StudentLayoutComponent implements OnInit {

  student: any = {
    fullName: 'Student',
    initials: 'ST'
  };

  currentPath = 'Dashboard';
  isHosteler = false;

  // Maps route segment → human-readable breadcrumb label
  private pathLabels: Record<string, string> = {
    'dashboard':            'Dashboard',
    'report-card':          'Report Card',
    'my-complaints':        'My Complaints',
    'make-complaint':       'Make Complaint',
    'notifications':        'Notifications',
    'payments':             'Payment History',
    'subjects':             'Subject Registration',
    'ask-leave-permission': 'Apply for Leave',
    'leave-permissions':    'Leave History',
  };

  constructor(private router: Router) {}

  ngOnInit() {
    const studentData = JSON.parse(sessionStorage.getItem("student") || "{}");
    if (studentData && studentData.fullName) {
      this.student = {
        ...studentData,
        initials: this.getInitials(studentData.fullName)
      };
    }

    // Determine if this student is a hosteler to show hostel nav links
    this.isHosteler = this.student?.studentType?.toLowerCase() === 'hosteler';

    // Update breadcrumb whenever the route changes
    this.router.events.pipe(
      filter(e => e instanceof NavigationEnd),
      map((e: any) => {
        const segments = e.urlAfterRedirects.split('/').filter(Boolean);
        const last = segments[segments.length - 1];
        return this.pathLabels[last] || this.capitalise(last);
      })
    ).subscribe(label => this.currentPath = label);

    // Set breadcrumb for initial load
    const segments = this.router.url.split('/').filter(Boolean);
    const last = segments[segments.length - 1];
    this.currentPath = this.pathLabels[last] || this.capitalise(last);
  }

  private capitalise(s: string): string {
    return s ? s.charAt(0).toUpperCase() + s.slice(1).replace(/-/g, ' ') : '';
  }

  getInitials(name: string): string {
    return name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0, 2);
  }

  logout() {
    sessionStorage.removeItem("studentLoggedIn");
    sessionStorage.removeItem("student");
    this.router.navigate(['/']);
  }
}