import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-assign-mentees-component',
  templateUrl: './assign-mentees-component.html',
  styleUrls: ['./assign-mentees-component.css']
})
export class AssignMenteesComponent {
  message = '';
  loading = false;

  // Inject HttpClient properly
  constructor(private http: HttpClient) {}

  assignMentees(): void {
    this.loading = true;
    this.message = 'Assigning mentees... Please wait.';

    // ✅ Correct backend API call (assuming your backend runs on port 8083)
    this.http.post('http://localhost:8083/admin/assign-mentees', {}, { responseType: 'text' })
      .subscribe({
        next: (response: string) => {
          this.loading = false;
          this.message = response || 'Mentees assigned successfully.';
        },
        error: (error) => {
          console.error('Error assigning mentees:', error);
          this.loading = false;
          this.message = 'Error assigning mentees. Please try again.';
        }
      });
  }
}