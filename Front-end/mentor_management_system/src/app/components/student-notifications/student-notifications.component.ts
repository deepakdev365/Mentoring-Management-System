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
    const data = sessionStorage.getItem("student");
    let apiUrl = 'http://localhost:8081/api/notifications/all';

    if (data) {
      const student = JSON.parse(data);
      if (student?.registrationNumber) {
        apiUrl = `http://localhost:8081/api/notifications/student/${student.registrationNumber}`;
      }
    }

    this.http.get<any[]>(apiUrl).subscribe(res=>{
      this.notifications = res;
    });
  }
}
