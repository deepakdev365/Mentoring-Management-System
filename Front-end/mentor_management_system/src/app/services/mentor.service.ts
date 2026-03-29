import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MentorService {

  private baseUrl = 'http://localhost:8081/mentor';

  constructor(private http: HttpClient) {}

login(email: string, password: string){
  console.log("SERVICE CALL:", email, password); // 🔥 DEBUG

  return this.http.post(
    `http://localhost:8081/mentor/login?email=${email}&password=${password}`,
    {}
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


uploadBacklogExcel(data: FormData) {
  return this.http.post(
    'http://localhost:8081/api/backlogs/upload',
    data,
    { responseType: 'text' }
  );
}
getStudentBacklogsByEmail(email: string) {
  return this.http.get<any>(
    `http://localhost:8081/api/backlogs/student/${encodeURIComponent(email)}`
  );
}

deleteBacklogById(id: number) {
  return this.http.delete(
    `http://localhost:8081/api/backlogs/${id}`,
    { responseType: 'text' }
  );
}

}
