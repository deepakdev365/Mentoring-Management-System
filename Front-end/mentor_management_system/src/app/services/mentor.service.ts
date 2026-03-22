import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MentorService {

  private baseUrl = 'http://localhost:8081/mentor';

  constructor(private http: HttpClient) {}

login(email: string, password: string) {
  return this.http.post<any>(
    `${this.baseUrl}/login?email=${email}&password=${password}`,
    null
  );
}



 getMentors(): Observable<any[]> {
  return this.http.get<any[]>('http://localhost:8081/mentor/all');
}

 uploadExcel(data: FormData){

    return this.http.post(`${this.baseUrl}/upload`, data, {responseType:'text'});

  }


getStudentsByMentorId(id: number) {
  return this.http.get<any[]>(`http://localhost:8081/api/students/mentor/${id}`);
}
}
