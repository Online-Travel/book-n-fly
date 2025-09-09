// support-ticket.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../Auth/auth';

@Injectable({
  providedIn: 'root'
})
export class SupportTicketService {
  private apiUrl = 'http://localhost:8080/api/support-tickets';

  constructor(private http: HttpClient, private auth: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.auth.getToken();
    return new HttpHeaders({
      Authorization: token ? `Bearer ${token}` : ''
    });
  }

  // Get tickets assigned to this travel agent
  getAssignedTickets(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/assigned`, { headers: this.getHeaders() });
  }

  // Close ticket
  closeTicket(ticketId: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/${ticketId}/close`, {}, { headers: this.getHeaders() });
  }
}
