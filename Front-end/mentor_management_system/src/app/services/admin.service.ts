import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { Admin } from '../models/admin'

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = 'http://localhost:8081/admin'

  constructor(private http: HttpClient) {}

  loginAdmin(admin: Admin): Observable<string> {
    return this.http.post<string>(this.baseUrl + '/login', admin, {responseType:'text' as 'json'})
  }



assignMentees(mentorId: number, registrationNumbers: string[]) {

  const body = {
    mentorId: mentorId,
    registrationNumbers: registrationNumbers
  };

  return this.http.post(
    'http://localhost:8081/admin/assign-mentor',
    body,
    { responseType: 'text' } 
  );
}

}