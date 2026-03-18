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
  selectedStudents:number[] = [];

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

  toggleStudent(id:number){

    if(this.selectedStudents.includes(id)){
      this.selectedStudents =
        this.selectedStudents.filter(s => s !== id);
    } else {
      this.selectedStudents.push(id);
    }

  }

 assign() {

  this.adminService.assignMentees(
    this.selectedMentorId,
    this.selectedStudents
  ).subscribe({
    next: () => alert("Assigned successfully"),
    error: (err) => {
      console.log(err);
      alert("Assignment failed");
    }
  });
}
}