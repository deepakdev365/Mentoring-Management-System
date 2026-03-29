import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StudentMark } from '../models/student-mark';

@Injectable({
  providedIn: 'root'
})
export class MarksService {
  private readonly baseUrl = 'http://localhost:8081/api/marks';

  constructor(private http: HttpClient) {}

  uploadMarks(file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.baseUrl}/upload`, formData, { responseType: 'text' });
  }

  getAllMarks(): Observable<StudentMark[]> {
    return this.http.get<StudentMark[]>(`${this.baseUrl}/all`);
  }

  getMarksByRollNo(rollNo: string): Observable<StudentMark[]> {
    return this.http.get<StudentMark[]>(`${this.baseUrl}/roll/${rollNo}`);
  }
}
