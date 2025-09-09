import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Review, SupportTicket } from '../models/review.model';
import { AuthService } from '../../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient, private authService: AuthService) {}

  // ==============================
  // Review URLs
  // ==============================
  private getReviewsByHotelUrl = 'http://localhost:8080/api/reviews/hotel';
  private getAllReviewsUrl = 'http://localhost:8080/api/reviews/all';

  // ==============================
  // Headers
  // ==============================
  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // ==============================
  // Review methods
  // ==============================
  getReviewsByHotel(hotelId: number): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.getReviewsByHotelUrl}/${hotelId}`, { headers: this.getHeaders() });
  }

  getAllReviews(): Observable<Review[]> {
    return this.http.get<Review[]>(this.getAllReviewsUrl, { headers: this.getHeaders() });
  }
  // ==============================
  // Support Ticket URLs
  // ==============================
  private getTicketUrl = 'http://localhost:8080/api/support-tickets';
  private getAllTicketsUrl = 'http://localhost:8080/api/support-tickets/all';
  private getOpenTicketsUrl = 'http://localhost:8080/api/support-tickets/open';
  private assignTicketUrl = 'http://localhost:8080/api/support-tickets';
  private getTicketByIdUrl = 'http://localhost:8080/api/support-tickets';


  // ==============================
  // Support Ticket methods
  // ==============================
  getTicket(ticketId: number): Observable<SupportTicket> {
    return this.http.get<SupportTicket>(`${this.getTicketUrl}/${ticketId}`, { headers: this.getHeaders() });
  }

  getAllTickets(): Observable<SupportTicket[]> {
    return this.http.get<SupportTicket[]>(this.getAllTicketsUrl, { headers: this.getHeaders() });
  }

  getOpenTickets(): Observable<SupportTicket[]> {
    return this.http.get<SupportTicket[]>(this.getOpenTicketsUrl, { headers: this.getHeaders() });
  }
  getTicketById(id: number): Observable<SupportTicket> {
  return this.http.get<SupportTicket>(`${this.getTicketByIdUrl}/${id}`, { headers: this.getHeaders() });
}


  assignTicket(ticketId: number, agentId: number): Observable<SupportTicket> {
    return this.http.put<SupportTicket>(
      `${this.assignTicketUrl}/${ticketId}/assign/${agentId}`,
      {},
      { headers: this.getHeaders() }
    );
  }
}