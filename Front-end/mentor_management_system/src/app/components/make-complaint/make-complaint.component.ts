import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-make-complaint',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './make-complaint.component.html'
})
export class MakeComplaintComponent implements OnInit {

  message = '';
  student: any;

  constructor(private http: HttpClient) {}

  ngOnInit(){
    this.student = JSON.parse(sessionStorage.getItem("student")!);
  }

  sendComplaint(){

    if(!this.message){
      alert("Enter message");
      return;
    }

    const body = {
      message: this.message,
      registrationNumber: this.student.registrationNumber,
      mentorId: this.student.mentor.id
    };

    this.http.post('http://localhost:8081/complaint/add', body)
      .subscribe({
        next: () => {
          alert("Complaint sent successfully");
          this.message = '';
        },
        error: (err) => {
          console.log(err);
          alert("Failed to send complaint");
        }
      });

  }

}