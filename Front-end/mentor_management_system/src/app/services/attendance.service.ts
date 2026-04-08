import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {
  private readonly baseUrl = 'http://localhost:8081/attendance';

  constructor(private http: HttpClient) {}

  uploadAttendanceExcel(formData: FormData): Observable<any> {
    return this.http.post(`${this.baseUrl}/upload`, formData, { responseType: 'text' as 'json' });
  }
}
