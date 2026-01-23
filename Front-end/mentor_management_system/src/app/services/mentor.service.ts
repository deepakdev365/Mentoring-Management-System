import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MentorService {

  private baseUrl = 'http://localhost:8080/mentor';

  constructor(private http: HttpClient) {}

  login(data: any) {
    return this.http.post(`${this.baseUrl}/login`, data, {
      responseType: 'text'
    });
  }
}
