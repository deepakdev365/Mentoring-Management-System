import { Component } from '@angular/core';
import { Router, RouterOutlet, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-mentor-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './mentor-layout.component.html'
})
export class MentorLayoutComponent {

  mentor:any;

  constructor(private router: Router) {}

  ngOnInit(){

    const email = localStorage.getItem('mentorEmail');

    if (!email) {
      this.router.navigate(['/mentor-login']);
      return;
    }

    this.mentor = {
      fullName: localStorage.getItem('mentorName')
    };
  }

  logout(){
    localStorage.clear();
    this.router.navigate(['/']);
  }

  goToProfile(){
    this.router.navigate(['/mentor/profile']);
  }

}