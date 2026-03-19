import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentService } from '../../services/student.service';
import { MentorService } from '../../services/mentor.service';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-assign-mentees',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './assign-mentees.component.html'
})
export class AssignMenteesComponent implements OnInit {

  mentors:any[] = [];
  students:any[] = [];

  selectedMentorId:any = null;
  selectedStudents:string[] = [];

  successMsg = '';
  errorMsg = '';

  constructor(
    private studentService: StudentService,
    private mentorService: MentorService,
    private adminService: AdminService
  ){}

  ngOnInit(){

    this.studentService.getStudents().subscribe((data:any[])=>{
      this.students = data;
    });

    this.mentorService.getMentors().subscribe((data:any[])=>{
      this.mentors = data;
    });

  }

  selectMentor(id:number){
    this.selectedMentorId = id;
  }
onStudentSelect(regNo: string, event: any) {

  if (event.target.checked) {
    this.selectedStudents.push(regNo);
  } else {
    this.selectedStudents =
      this.selectedStudents.filter(s => s !== regNo);
  }
   console.log(this.selectedStudents); 
}
  

 assign() {

  if (!this.selectedMentorId) {
    alert("Select a mentor");
    return;
  }

  if (this.selectedStudents.length === 0) {
    alert("Select students");
    return;
  }

  this.adminService.assignMentees(
    this.selectedMentorId,
    this.selectedStudents   
  ).subscribe({
    next: (res) => {
      console.log(res);
      alert("Assigned successfully");
    },
    error: (err) => {
      console.log(err);
      alert("Assignment failed");
    }
  });

}
}