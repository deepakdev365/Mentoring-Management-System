import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StudentModel } from '../models/student.model';

@Injectable({ providedIn: 'root' })
export class Student {
  private baseUrl = 'http://localhost:8083/api/students';

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<string> {
    return this.http.post(`${this.baseUrl}/login`, { email, password }, { responseType: 'text' });
  }

  getAllStudents(): Observable<StudentModel[]> {
    return this.http.get<StudentModel[]>('http://localhost:8083/api/students/all');

  }

  addStudent(student: StudentModel): Observable<StudentModel> {

    return this.http.post<StudentModel>(`${this.baseUrl}/add`, student);
  }

  getStudentByEmail(email: string): Observable<StudentModel> {
    return this.http.get<StudentModel>(`${this.baseUrl}/email/${email}`);
  }
  
loadStudentData(email: string): Observable<StudentModel[]> {
  return this.http.get<StudentModel[]>(`${this.baseUrl}/email/${email}`);
}
}