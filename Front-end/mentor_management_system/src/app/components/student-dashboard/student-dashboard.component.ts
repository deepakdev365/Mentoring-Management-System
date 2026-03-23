import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './student-dashboard.component.html',
  styleUrls: ['./student-dashboard.component.css']
})
export class StudentDashboardComponent implements OnInit {

  student: any;

  constructor(private router: Router) {}

  ngOnInit(): void {

    const data = sessionStorage.getItem("student");

    if(data){
      this.student = JSON.parse(data);
      console.log("Student Data:", this.student);
    }

  }

  // navigate to mentor profile
  viewMentor(){

    if(this.student?.mentor){
      this.router.navigate(['/mentor', this.student.mentor.id]);
    } else {
      alert("No mentor assigned");
    }

  }

  // navigate to complaint page
  goToComplaint(){
    this.router.navigate(['/make-complaint']);
  }

  // navigate to complaints list
  viewMyComplaints(){
    this.router.navigate(['/my-complaints']);
  }

}