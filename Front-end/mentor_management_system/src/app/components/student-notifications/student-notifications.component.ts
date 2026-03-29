import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './student-notifications.component.html'
})
export class StudentNotificationsComponent implements OnInit {

  notifications:any[] = [];

  constructor(private http: HttpClient){}

  ngOnInit(){

    this.http.get<any[]>(
      'http://localhost:8081/api/notifications/all'
    ).subscribe(res=>{
      this.notifications = res;
    });
  }
}
