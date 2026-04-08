import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-student-payment-history',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './student-payment-history.component.html',
  styleUrls: ['./student-payment-history.component.css']
})
export class StudentPaymentHistoryComponent implements OnInit {

  payments: any[] = [];
  student: any;
  loading = true;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const data = sessionStorage.getItem("student");
    if (data) {
      this.student = JSON.parse(data);
      this.fetchPaymentHistory();
    }
  }

  fetchPaymentHistory() {
    if (this.student?.registrationNumber) {
      this.http.get<any[]>(`http://localhost:8081/payment/roll/${this.student.registrationNumber}`)
        .subscribe({
          next: (res) => {
            this.payments = res;
            this.loading = false;
          },
          error: (err) => {
            console.error("Error fetching payments", err);
            this.loading = false;
          }
        });
    }
  }

  getStatusClass(status: string) {
    switch (status?.toLowerCase()) {
      case 'success': return 'badge bg-success-soft text-success';
      case 'pending': return 'badge bg-warning-soft text-warning';
      case 'failed': return 'badge bg-danger-soft text-danger';
      default: return 'badge bg-light text-muted';
    }
  }
}
