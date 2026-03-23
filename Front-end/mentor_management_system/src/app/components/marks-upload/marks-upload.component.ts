import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-marks-upload',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './marks-upload.component.html'
})
export class MarksUploadComponent {

  selectedFile!: File;

  constructor(private service: StudentService) {}

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
  }

 upload() {
    if (!this.selectedFile) {
      alert("Please select file");
      return;
    }

    this.service.uploadMarksExcel(this.selectedFile)
      .subscribe(res => {
        alert("Marks Uploaded Successfully ✅");
      });
  }
}