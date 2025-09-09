import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Hotel } from '../../admin/models/booking.model';
import { AuthService } from '../../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class HotelManager {
  private baseUrl = 'http://localhost:8080/api/hotels';

  constructor(private http: HttpClient, private authService: AuthService) { }

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken(); // Implement in AuthService
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  getMyHotels(): Observable<Hotel[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<Hotel[]>(`${this.baseUrl}/my-hotels`, { headers });
  }
  
}
