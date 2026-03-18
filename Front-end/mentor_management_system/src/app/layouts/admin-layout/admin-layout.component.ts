import { Component } from '@angular/core';
import { Router, RouterOutlet, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './admin-layout.component.html'
})
export class AdminLayoutComponent {

  constructor(private router: Router) {}

  logout(){

    sessionStorage.removeItem("adminLoggedIn");
    sessionStorage.removeItem("admin");

    this.router.navigate(['/']);

  }

  admin:any;

ngOnInit(){
  this.admin = JSON.parse(sessionStorage.getItem("admin") || "{}");
}

}