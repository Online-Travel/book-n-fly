import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../Auth/auth';

@Injectable({ providedIn: 'root' })
export class ItineraryService {
  private apiUrl = 'http://localhost:8080/api/itineraries';

  constructor(private http: HttpClient, private auth: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.auth.getToken();
    return new HttpHeaders({
      Authorization: token ? `Bearer ${token}` : ''
    });
  }

  // ----- For Admin -----
  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  // ----- For Agent -----
  getAgentItineraries(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/agent`, { headers: this.getHeaders() });
  }

  // ----- For Traveler -----
  getUserItineraries(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/${userId}`, { headers: this.getHeaders() });
  }

  create(itinerary: any): Observable<any> {
    return this.http.post(this.apiUrl, itinerary, { headers: this.getHeaders() });
  }

  update(id: number, itinerary: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, itinerary, { headers: this.getHeaders() });
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }
}
