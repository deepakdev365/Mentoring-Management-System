import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './admin-layout.component.html',
  styleUrl: './admin-layout.component.css'
})
export class AdminLayoutComponent implements OnInit {

  admin: any = {
    fullName: 'Administrator',
    initials: 'AD'
  };

  constructor(private router: Router) {}

  ngOnInit() {
    const adminData = JSON.parse(sessionStorage.getItem("admin") || "{}");
    if (adminData && adminData.fullName) {
      this.admin = {
        ...adminData,
        initials: this.getInitials(adminData.fullName)
      };
    }
  }

  getInitials(name: string): string {
    return name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0, 2);
  }

  logout() {
    sessionStorage.removeItem("adminLoggedIn");
    sessionStorage.removeItem("admin");
    this.router.navigate(['/']);
  }
}