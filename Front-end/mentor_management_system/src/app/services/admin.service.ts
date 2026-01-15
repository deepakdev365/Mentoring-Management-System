import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) {}

  loginAdmin(data: any) {
    return this.http.post(
      `${this.baseUrl}/login`,
      data,
      { responseType: 'text' } 
    );
  }
}
