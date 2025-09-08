import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';
import { CreateSupportTicket, SupportTicket } from './../models/supportTicket';

@Injectable({
  providedIn: 'root'
})
export class SupportTicketService {
  private baseUrl = 'http://localhost:8080/api/support-tickets';
  private isBrowser: boolean;

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object,
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  private getHeaders(): HttpHeaders {
    let headersConfig: { [name: string]: string } = {
      'Content-Type': 'application/json'
    };

    if (this.isBrowser) {
      const token = localStorage.getItem('auth_token'); // or 'authToken' based on your app
      if (token) {
        headersConfig['Authorization'] = `Bearer ${token}`;
      }
    }

    return new HttpHeaders(headersConfig);
  }

  createTicket(ticket: CreateSupportTicket): Observable<SupportTicket> {
    return this.http.post<SupportTicket>(
      this.baseUrl,
      ticket,
      { headers: this.getHeaders() }
    );
  }

  getMyTickets(): Observable<SupportTicket[]> {
    return this.http.get<SupportTicket[]>(
      `${this.baseUrl}/my`,
      { headers: this.getHeaders() }
    );
  }

  getTicketById(ticketId: number): Observable<SupportTicket> {
    return this.http.get<SupportTicket>(
      `${this.baseUrl}/${ticketId}`,
      { headers: this.getHeaders() }
    );
  }

  updateTicket(ticketId: number, updateData: Partial<SupportTicket>): Observable<SupportTicket> {
    return this.http.put<SupportTicket>(
      `${this.baseUrl}/${ticketId}`,
      updateData,
      { headers: this.getHeaders() }
    );
  }

  deleteTicket(ticketId: number): Observable<any> {
    return this.http.delete(
      `${this.baseUrl}/${ticketId}`,
      { headers: this.getHeaders() }
    );
  }
}
