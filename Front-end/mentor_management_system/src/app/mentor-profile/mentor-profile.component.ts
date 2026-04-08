import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
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

  constructor(
    private mentorService: MentorService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      const email = params['email'] || localStorage.getItem('mentorEmail');

      if (email) {
        this.mentorService.getMentors().subscribe((list: any[]) => {
          this.mentor = list.find(
            (m: any) =>
              m.email?.trim().toLowerCase() === email?.trim().toLowerCase()
          );
          console.log("FOUND MENTOR:", this.mentor);
        });
      }
    });
  }
}