import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StudentService } from '../../services/student.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './student-login.component.html',
  styleUrl: './student-login.component.css'
})
export class StudentLoginComponent implements OnInit {

  loginData = {
    email: '',
    password: ''
  };

  successMsg = '';
  errorMsg = '';

  constructor(
    private studentService: StudentService,
    private router: Router
  ) {}

  login(){

    this.studentService.loginStudent(
      this.loginData.email,
      this.loginData.password
    ).subscribe({

      next:(res:any)=>{

        console.log(res);

        this.successMsg = "Login Successful";
        this.errorMsg = "";

        // store login state
        sessionStorage.setItem("studentLoggedIn","true");

        // store student data
        sessionStorage.setItem("student", JSON.stringify(res));

        setTimeout(()=>{
          this.router.navigate(['/student/dashboard'], {replaceUrl:true});
        },800);

      },

      error:(err)=>{

        console.log(err);

        this.successMsg = "";

        if(err.error){
          this.errorMsg = err.error;
        }
        else{
          this.errorMsg = "Invalid Email or Password";
        }

      }

    });

  }

  ngOnInit(){

    const loggedIn = sessionStorage.getItem("studentLoggedIn");

    if(loggedIn === "true"){
      this.router.navigate(['/student/dashboard']);
    }

  }

}