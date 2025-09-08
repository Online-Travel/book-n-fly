import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Booking {
  bookingId: number;
  itemId: number;
  userId: number;
  type: string;
  status: string;
  paymentId: number;
}

export interface Itinerary {
  itineraryId: number;
  userId: number;
  packageId: number;
  customizationDetails: string;
  startDate: string;
  endDate: string;
  numberOfTravelers: number;
  totalPrice: number | null;
  status: string;
}

export interface Review {
  hotelId: number;
  rating: number;
  comment: string;
}

export interface ItineraryUpdate {
  packageId: number;
  customizationDetails: string;
  startDate: string;
  endDate: string;
  numberOfTravelers: number;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
private bookingsApiUrl = 'http://localhost:8080/api/bookings/my-bookings';
  private reviewsApiUrl = 'http://localhost:8080/api/reviews';
  private itinerariesApiUrl = 'http://localhost:8080/api/itineraries/user';
  private itineraryUpdateApiUrl = 'http://localhost:8080/api/itineraries';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Bookings API calls
  getMyBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(this.bookingsApiUrl, { 
      headers: this.getHeaders() 
    });
  }

  // Reviews API calls
  submitReview(review: Review): Observable<any> {
    return this.http.post(this.reviewsApiUrl, review, {
      headers: this.getHeaders()
    });
  }

  // Itineraries API calls
  getMyItineraries(): Observable<Itinerary[]> {
    return this.http.get<Itinerary[]>(this.itinerariesApiUrl, {
      headers: this.getHeaders()
    });
  }

  updateItinerary(id: number, itinerary: ItineraryUpdate): Observable<any> {
    return this.http.put(`${this.itineraryUpdateApiUrl}/${id}`, itinerary, {
      headers: this.getHeaders()
    });
  }

  deleteItinerary(id: number): Observable<any> {
    return this.http.delete(`${this.itineraryUpdateApiUrl}/${id}`, {
      headers: this.getHeaders()
    });
  }
}