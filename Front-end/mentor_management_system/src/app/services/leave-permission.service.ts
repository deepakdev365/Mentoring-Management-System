import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LeavePermissionService {

  private apiUrl = 'http://localhost:8081/leave';

  constructor(private http: HttpClient) { }

  // Apply for leave (Student)
  applyLeave(leaveData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/apply`, leaveData);
  }

  // Get all leaves for a specific student (Student)
  getStudentLeaves(studentId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/student/${studentId}`);
  }

  // Get all leaves (Admin)
  getAllLeaves(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }

  // Get leaves for a specific mentor's students
  getLeavesByMentorId(mentorId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/mentor/${mentorId}`);
  }

  // Get single leave by ID
  getLeaveById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Approve leave (Admin/Mentor)
  approveLeave(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/approve/${id}`, {});
  }

  // Reject leave (Admin/Mentor)
  rejectLeave(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/reject/${id}`, {});
  }
}
