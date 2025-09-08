import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../services/Auth/auth';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-support-ticket',
  standalone:true,
  imports:[FormsModule,CommonModule],
  templateUrl: './supportticket.html',
  styleUrls: ['./supportticket.css']
})
export class SupportTicketComponent implements OnInit {
  tickets: any[] = [];
  loading = false;

  private apiUrl = 'http://localhost:8085/api/support-tickets/assigned';

  constructor(private http: HttpClient, private auth: AuthService) {}

  ngOnInit(): void {
    this.loadTickets();
  }

  loadTickets(): void {
    this.loading = true;
    const token = this.auth.getToken();

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    this.http.get<any[]>(this.apiUrl, { headers }).subscribe({
      next: (res) => {
        this.tickets = res;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching tickets', err);
        this.loading = false;
      }
    });
  }

  closeTicket(ticketId: number): void {
    const token = this.auth.getToken();

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    this.http
      .put(`${'http://localhost:8085/api/support-tickets'}/${ticketId}/close`, {}, { headers })
      .subscribe({
        next: () => {
          // Refresh tickets after closing one
          this.loadTickets();
        },
        error: (err) => {
          console.error('Error closing ticket', err);
        }
      });
  }
}
