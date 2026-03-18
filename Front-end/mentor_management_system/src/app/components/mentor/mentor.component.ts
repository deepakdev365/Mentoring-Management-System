import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MentorService } from '../../services/mentor.service';

@Component({
  selector: 'app-mentor',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mentor.component.html',
  styleUrl: './mentor.component.css'
})
export class MentorComponent implements OnInit {

  mentors: any[] = [];

  selectedFile!: File;

  constructor(
    private mentorService: MentorService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadMentors();
  }

  loadMentors(){

    this.mentorService.getMentors().subscribe((data:any[]) => {

      this.mentors = data;

      this.cdr.detectChanges();

    });

  }

  onFileSelected(event:any){

    this.selectedFile = event.target.files[0];

  }

  uploadFile(){

    const formData = new FormData();

    formData.append("file", this.selectedFile);

    this.mentorService.uploadExcel(formData).subscribe(res => {

      console.log(res);

      alert("Excel Uploaded Successfully");

      this.loadMentors(); // reload table

    });

  }

  logout(){
    sessionStorage.removeItem('adminLoggedIn');
    this.router.navigate(['/']);
  }

}