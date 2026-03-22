import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
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
    private studentService: StudentService,
    private router: Router
  ){}

  ngOnInit(){

    const regNo = this.route.snapshot.paramMap.get('regNo');

    this.studentService.getStudentByRegNo(regNo)
      .subscribe(res=>{
        this.student = res;
      });

  }
  unassign(){

  if(!confirm("Are you sure to unassign this student?")) return;

  this.studentService.unassignMentor(this.student.registrationNumber)
    .subscribe({

      next: (res) => {
        alert(res);

        // 🔥 update UI instantly
        this.student.mentor = null;
      },

      error: (err) => {
        console.log(err);
        alert("Unassign failed");
      }

    });

}
goToAssign(){

  this.router.navigate(['/admin/assign-mentees'], {
    queryParams: {
      regNo: this.student.registrationNumber
    }
  });

}
}