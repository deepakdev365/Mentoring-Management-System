import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './make-announcement.component.html'
})
export class MakeAnnouncementComponent {

  message = '';
  response = '';

  constructor(private http: HttpClient){}

  send(){

    this.http.post(
      'http://localhost:8081/api/notifications/create',
      { message: this.message },
      { responseType: 'text' }
    ).subscribe(res=>{
      this.response = res;
      this.message = '';
    });
  }
}