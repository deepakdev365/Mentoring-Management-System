import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-mentor-complaints',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mentor-complaints.component.html'
})
export class MentorComplaintsComponent implements OnInit {

  complaints:any[] = [];
  mentorId:any;

  constructor(private http: HttpClient){}

  ngOnInit(){

    this.mentorId = localStorage.getItem("mentorId");

    this.loadComplaints();
  }

  loadComplaints(){
    this.http.get(
      `http://localhost:8081/complaint/mentor/${this.mentorId}`
    ).subscribe((res:any)=>{
      this.complaints = res;
    });
  }

  resolve(id:number){

    this.http.put(
      `http://localhost:8081/complaint/update-status/${id}?status=Resolved`,
      {}
    ).subscribe(()=>{
      alert("Marked as resolved");
      this.loadComplaints();
    });

  }

}