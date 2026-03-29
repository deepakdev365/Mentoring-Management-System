import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MentorService } from '../services/mentor.service';

@Component({
  selector: 'app-mentor-profile',
  standalone: true,
  imports: [CommonModule],   // ✅ IMPORTANT
  templateUrl: './mentor-profile.component.html',
  styleUrls: ['./mentor-profile.component.css']
})
export class MentorProfileComponent implements OnInit {

  mentor: any;

  constructor(private mentorService: MentorService) {}

  ngOnInit() {

    const email = localStorage.getItem('mentorEmail');

    this.mentorService.getMentors().subscribe((list: any[]) => {

      this.mentor = list.find(
        (m: any) =>
          m.email?.trim().toLowerCase() === email?.trim().toLowerCase()
      );

      console.log("FOUND:", this.mentor);
    });
  }
}