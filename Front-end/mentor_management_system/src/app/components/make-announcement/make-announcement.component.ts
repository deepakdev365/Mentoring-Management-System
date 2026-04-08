import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './make-announcement.component.html',
  styleUrl: './make-announcement.component.css'
})
export class MakeAnnouncementComponent {

  message = '';
  response = '';
  isSending = false;

  constructor(private http: HttpClient){}

  send() {
    if (!this.message.trim()) {
      alert("Please enter a message to broadcast.");
      return;
    }

    this.isSending = true;
    this.response = '';

    this.http.post(
      'http://localhost:8081/api/notifications/create',
      { message: this.message },
      { responseType: 'text' }
    ).subscribe({
      next: (res) => {
        this.response = "Broadcast successful! All students have been notified.";
        this.message = '';
        this.isSending = false;
      },
      error: (err) => {
        console.error(err);
        this.response = "Broadcasting failed. Please try again.";
        this.isSending = false;
      }
    });

  }
}