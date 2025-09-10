import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../../auth/auth.service';


export interface Hotel {
  hotelId?: number;
  name: string;
  location: string;
  roomsAvailable: number;
  rating: number;
  pricePerNight: number;
  userId?: number;
}


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

  createHotel(hotel: Hotel): Observable<Hotel> {
    return this.http.post<Hotel>(`${this.baseUrl}`, hotel, {
      headers: this.getAuthHeaders()
    });
  }

  updateHotel(hotelId: number, hotel: Hotel): Observable<Hotel> {
    return this.http.put<Hotel>(`${this.baseUrl}/${hotelId}`, hotel, {
      headers: this.getAuthHeaders()
    });
  }
  
}
