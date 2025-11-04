import { Component, OnInit } from '@angular/core';
import { Mentor} from '../../../services/mentor';
import { MentorModel } from '../../../models/mentor.model'; 
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-view-mentors',
  imports: [CommonModule],
  templateUrl: './view-mentors-component.html',
  styleUrls: ['./view-mentors-component.css']
})
export class ViewMentorsComponent implements OnInit {
  mentors: MentorModel[] = [];

  constructor(private mentor: Mentor) {}

  ngOnInit(): void {
    this.loadMentors();
  }

  loadMentors() {
    this.mentor.getAllMentors().subscribe({
      next: (data) => this.mentors = data,
      error: (err) => console.error('Error fetching mentors:', err)
    });
  }
}