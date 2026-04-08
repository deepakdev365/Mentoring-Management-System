import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BacklogService {
  private baseUrl = 'http://localhost:8081/api/backlogs';

  constructor(private http: HttpClient) {}

  getBacklogsByEmail(email: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/student/${encodeURIComponent(email)}`);
  }
}
