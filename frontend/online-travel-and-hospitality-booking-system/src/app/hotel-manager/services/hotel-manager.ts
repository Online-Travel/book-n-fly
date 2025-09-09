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
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getMyHotels(): Observable<Hotel[]> {
    const headers = this.getAuthHeaders();
    // return this.http.get<Hotel[]>(`${this.baseUrl}/my-hotels`, { headers });
    let hotels = this.http.get<Hotel[]>(`${this.baseUrl}/my-hotels`, { headers });
    console.log(hotels);
    return hotels;
  }

  createHotel(hotel: Hotel): Observable<Hotel> {
    const headers = this.getAuthHeaders();
    console.log(headers);
    return this.http.post<Hotel>(`${this.baseUrl}`, hotel, { headers });
  }

  updateHotel(hotelId: number, hotel: Hotel): Observable<Hotel> {
    const headers = this.getAuthHeaders();
    return this.http.put<Hotel>(`${this.baseUrl}/${hotelId}`, hotel, { headers });
  }
}
