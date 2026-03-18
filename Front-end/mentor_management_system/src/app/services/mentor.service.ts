import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MentorService {

  private baseUrl = 'http://localhost:8081/mentor';

  constructor(private http: HttpClient) {}

  login(data: any) {
    return this.http.post(`${this.baseUrl}/login`, data, {
      responseType: 'text'
    });
  }



 getMentors(): Observable<any[]> {
  return this.http.get<any[]>('http://localhost:8081/mentor/all');
}

 uploadExcel(data: FormData){

    return this.http.post(`${this.baseUrl}/upload`, data, {responseType:'text'});

  }
}
