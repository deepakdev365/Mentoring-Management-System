import { Component } from '@angular/core';
import { Router, RouterOutlet, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-student-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './student-layout.component.html'
})
export class StudentLayoutComponent {

  constructor(private router: Router) {}

  logout(){

    sessionStorage.removeItem("studentLoggedIn");
    sessionStorage.removeItem("student");

    this.router.navigate(['/']);

  }

  student:any;

ngOnInit(){
  this.student = JSON.parse(sessionStorage.getItem("student") || "{}");
}

}