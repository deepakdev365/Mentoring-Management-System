import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ComplaintModel } from '../models/complaint.model';

@Injectable({ providedIn: 'root' }) // ✅ Must have this decorator
export class Complaint { // ✅ This is your service class name
  private baseUrl = 'http://localhost:8083/api/complaints';

  constructor(private http: HttpClient) {} // ✅ HttpClient must be imported

  addComplaint(complaint: ComplaintModel): Observable<ComplaintModel> {
    return this.http.post<ComplaintModel>(`${this.baseUrl}/add`, complaint);
  }

  getComplaintsByMentor(email: string): Observable<ComplaintModel[]> {
    return this.http.get<ComplaintModel[]>(`${this.baseUrl}/mentor/${email}`);
  }

  getComplaintsByStudent(email: string): Observable<ComplaintModel[]> {
    return this.http.get<ComplaintModel[]>(`${this.baseUrl}/student/${email}`);
  }
}