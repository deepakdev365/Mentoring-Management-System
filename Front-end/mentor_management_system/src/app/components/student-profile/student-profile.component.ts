import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-student-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './student-profile.component.html'
})
export class StudentProfileComponent implements OnInit {

  student:any;

  constructor(
    private route: ActivatedRoute,
    private studentService: StudentService
  ){}

  ngOnInit(){

    const regNo = this.route.snapshot.paramMap.get('regNo');

    this.studentService.getStudentByRegNo(regNo)
      .subscribe(res=>{
        this.student = res;
      });

  }
}