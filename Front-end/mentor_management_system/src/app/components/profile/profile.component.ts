import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // ✅ ADD THIS
import { MentorService } from '../../services/mentor.service'; // ✅ USE RELATIVE PATH

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule], // ✅ ADD HERE
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  mentor: any = {};

  constructor(private mentorService: MentorService) {}

    ngOnInit() {
  const email = localStorage.getItem('mentorEmail');
  console.log("EMAIL:", email);

  this.mentorService.getMentors().subscribe((list: any[]) => {
    console.log("API DATA:", list);

    this.mentor = list.find(
      (m: any) =>
        m.email?.trim().toLowerCase() === email?.trim().toLowerCase()
    );

    console.log("FOUND:", this.mentor);
  });
}

}