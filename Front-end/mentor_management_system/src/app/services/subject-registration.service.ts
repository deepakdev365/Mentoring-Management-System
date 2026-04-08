import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubjectRegistrationService {
  private readonly baseUrl = 'http://localhost:8081/subject';

  constructor(private http: HttpClient) {}

  uploadSubjectRegistrationExcel(formData: FormData): Observable<any> {
    return this.http.post(`${this.baseUrl}/upload`, formData, { responseType: 'text' as 'json' });
  }

  getRegistrationsByRegNo(regNo: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/student/${regNo}`);
  }
}
