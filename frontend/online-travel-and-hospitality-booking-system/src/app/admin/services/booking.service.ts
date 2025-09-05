import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../../auth/auth.service';
import { Booking, Flight, Hotel } from '../models/booking.model';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private http: HttpClient, private authService: AuthService) {}

  // ==============================
  // Booking URLs
  // ==============================
  private getAllBookingsUrl = 'http://localhost:8080/api/bookings';
  private getBookingByIdUrl = 'http://localhost:8080/api/bookings';

  // ==============================
  // Flight URLs
  // ==============================
  private getAllFlightsUrl = 'http://localhost:8080/api/flights';
  private getFlightByIdUrl = 'http://localhost:8080/api/flights';
  private createFlightUrl = 'http://localhost:8080/api/flights';
  private updateFlightUrl = 'http://localhost:8080/api/flights';
  private deleteFlightUrl = 'http://localhost:8080/api/flights';

  // ==============================
  // Hotel URLs
  // ==============================
  private getAllHotelsUrl = 'http://localhost:8080/api/hotels';
  private getHotelByIdUrl = 'http://localhost:8080/api/hotels';
  private deleteHotelUrl = 'http://localhost:8080/api/hotels';

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
  // Booking methods
  // ==============================
  getAllBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(this.getAllBookingsUrl, { headers: this.getHeaders() });
  }

  getBookingById(id: number): Observable<Booking> {
    return this.http.get<Booking>(`${this.getBookingByIdUrl}/${id}`, { headers: this.getHeaders() });
  }

  // ==============================
  // Flight methods
  // ==============================
  getAllFlights(): Observable<Flight[]> {
    return this.http.get<Flight[]>(this.getAllFlightsUrl, { headers: this.getHeaders() });
  }

  getFlightById(flightId: number): Observable<Flight> {
    return this.http.get<Flight>(`${this.getFlightByIdUrl}/${flightId}`, { headers: this.getHeaders() });
  }

  createFlight(flight: Flight): Observable<Flight> {
    return this.http.post<Flight>(this.createFlightUrl, flight, { headers: this.getHeaders() });
  }

  updateFlight(flightId: number, flight: Flight): Observable<Flight> {
    return this.http.put<Flight>(`${this.updateFlightUrl}/${flightId}`, flight, { headers: this.getHeaders() });
  }

  deleteFlight(flightId: number): Observable<void> {
    return this.http.delete<void>(`${this.deleteFlightUrl}/${flightId}`, { headers: this.getHeaders() });
  }

  // ==============================
  // Hotel methods
  // ==============================
  getAllHotels(): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(this.getAllHotelsUrl, { headers: this.getHeaders() });
  }

  getHotelById(hotelId: number): Observable<Hotel> {
    return this.http.get<Hotel>(`${this.getHotelByIdUrl}/${hotelId}`, { headers: this.getHeaders() });
  }

  deleteHotel(hotelId: number): Observable<void> {
    return this.http.delete<void>(`${this.deleteHotelUrl}/${hotelId}`, { headers: this.getHeaders() });
  }
}
