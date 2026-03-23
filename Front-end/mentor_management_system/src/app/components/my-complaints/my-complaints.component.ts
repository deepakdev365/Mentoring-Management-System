import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-my-complaints',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-complaints.component.html',
  styleUrls: ['./my-complaints.component.css']
})
export class MyComplaintsComponent implements OnInit {

  complaints: any[] = [];
  student: any;

  constructor(private http: HttpClient) {}

  ngOnInit(){

    this.student = JSON.parse(sessionStorage.getItem("student")!);

    this.loadComplaints();
  }

  loadComplaints(){

    this.http.get(
      `http://localhost:8081/complaint/student/${this.student.registrationNumber}`
    ).subscribe({
      next: (res: any) => {
        this.complaints = res;
        console.log("Complaints:", this.complaints);
      },
      error: (err) => {
        console.log(err);
      }
    });

  }

}