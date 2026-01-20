import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private baseUrl = 'http://localhost:8080/student';

  constructor(private http: HttpClient) {}

  loginStudent(data: any): Observable<string> {
    return this.http.post(
      `${this.baseUrl}/login`,
      data,
      { responseType: 'text' }
    );
  }
}
