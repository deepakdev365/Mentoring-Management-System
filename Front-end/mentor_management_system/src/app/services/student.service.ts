import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private baseUrl = 'http://localhost:8081/api/students';

  constructor(private http: HttpClient) {}

loginStudent(email: string, password: string){

  const formData = new FormData();

  formData.append("email", email);
  formData.append("password", password);

  return this.http.post(
    "http://localhost:8081/api/students/login",
    formData
  );

}

  getStudents(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/all`);
  }



 uploadExcel(data: FormData){
  return this.http.post(
    'http://localhost:8081/api/students/upload',
    data,
    { responseType: 'text' }
  );
}

getStudentByRegNo(regNo: any){
  return this.http.get(`http://localhost:8081/api/students/${regNo}`);
}

}