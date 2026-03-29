import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private baseUrl = 'http://localhost:8081/payment';

  constructor(private http: HttpClient) {}

  savePayment(payment: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/save`, payment);
  }

  getAllPayments(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/all`);
  }

  getPaymentsByEmails(emails: string[]) {
  return this.http.post<any[]>(`${this.baseUrl}/emails`, emails);
}

  uploadPaymentExcel(data: FormData): Observable<string> {
    return this.http.post(`${this.baseUrl}/upload`, data, {
      responseType: 'text'
    });
  }
  getPaymentByEmail(email: string) {
  return this.http.get<any>(`${this.baseUrl}/email/${encodeURIComponent(email)}`);
}
}