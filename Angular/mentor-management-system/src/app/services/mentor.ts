import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MentorModel } from '../models/mentor.model';

@Injectable({ providedIn: 'root' })
export class Mentor {
  private baseUrl = 'http://localhost:8083/mentors';

  constructor(private http: HttpClient) {}

  registerMentor(mentor: MentorModel): Observable<MentorModel> {
    return this.http.post<MentorModel>(`${this.baseUrl}/register`, mentor);
  }

  getAllMentors(): Observable<MentorModel[]> {
  return this.http.get<MentorModel[]>('http://localhost:8083/admin/mentors');
}
  getMentorByEmail(email: string): Observable<MentorModel> {
    return this.http.get<MentorModel>(`${this.baseUrl}/email/${email}`);
  }
   login(email: string, password: string): Observable<string> {
    return this.http.post(`${this.baseUrl}/login`, { email, password }, { responseType: 'text' });
  }
}