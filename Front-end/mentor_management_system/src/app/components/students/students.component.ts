import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentService } from '../../services/student.service';
import { Router } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';


@Component({
  selector: 'app-students',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './students.component.html',
  styleUrl: './students.component.css',
})
export class StudentsComponent implements OnInit {

  students: any[] = [];

  constructor(
  private studentService: StudentService,
  private router: Router,
  private cdr: ChangeDetectorRef
) {}
  ngOnInit(): void {

  this.studentService.getStudents().subscribe((data: any[]) => {

    console.log(data);
    this.students = data;

    this.cdr.detectChanges();   

  });

}

  logout() {
  sessionStorage.removeItem('adminLoggedIn');
  this.router.navigate(['/']);
}


selectedFile!: File;

onFileSelected(event:any){

  this.selectedFile = event.target.files[0];

}
loadStudents(){

  this.studentService.getStudents().subscribe((data:any[]) => {

    this.students = data;
    this.cdr.detectChanges();

  });

}
uploadMessage = '';
uploadFile(){

  if(!this.selectedFile){
    this.uploadMessage = "Please select a file first.";
    this.cdr.detectChanges();
    return;
  }

  const formData = new FormData();
  formData.append("file", this.selectedFile);

  this.studentService.uploadExcel(formData).subscribe({

    next: (res: string) => {

      console.log(res);

      this.uploadMessage = res;

      if(res.includes("Students uploaded successfully")){
        this.loadStudents();
      }

      this.cdr.detectChanges();

    },

    error: (err) => {

      console.error(err);

      this.uploadMessage = "Error uploading file.";

      this.cdr.detectChanges();

    }

  });

}
}