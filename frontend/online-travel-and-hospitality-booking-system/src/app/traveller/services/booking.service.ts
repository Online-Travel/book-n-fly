import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Hotel } from '../models/hotel';
import { Flight } from '../models/flight';
import { Package } from '../models/package';
import { Review } from '../models/review';
import { Itinerary } from './../models/itinerary';


@Injectable({
    providedIn: 'root'
})
export class BookingService {
private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  // Hotel APIs
  getAllHotels(): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(`${this.baseUrl}/hotels`);
  }

  searchHotels(destination?: string, minRating?: number, maxRating?: number, minPrice?:number, maxPrice?: number, minRooms?: number, maxRooms?:number): Observable<Hotel[]> {
    let params = '';
    if (destination) params += `destination=${destination}&`;
    if (minRating) params += `minRating=${minRating}&`;
    if (maxRating) params += `maxRating=${maxRating}&`;
    if (minPrice) params += `minPrice=${minPrice}&`;
    if (maxPrice) params += `maxPrice=${maxPrice}&`;
    if (minRooms) params += `minRooms=${minRooms}&`;
    if (maxRooms) params += `maxRooms=${maxRooms}&`;
    
    return this.http.get<Hotel[]>(`${this.baseUrl}/hotels/search?${params}`);
  }

  // Flight APIs
  getAllFlights(): Observable<Flight[]> {
    return this.http.get<Flight[]>(`${this.baseUrl}/flights`);
  }

  searchFlights(airline?: string, departure?: string, arrival?: string, minPrice?: number, maxPrice?: number, availability?: boolean): Observable<Flight[]> {
    let params = '';
    if (airline) params += `airline=${airline}&`;
    if (departure) params += `departure=${departure}&`;
    if (arrival) params += `arrival=${arrival}&`;
    if (minPrice) params += `minPrice=${minPrice}&`;
    if (maxPrice) params += `maxPrice=${maxPrice}&`;
    if (availability !== undefined) params += `availability=${availability}&`;
    
    return this.http.get<Flight[]>(`${this.baseUrl}/flights/search?${params}`);
  }

  // Package APIs
  getAllPackages(): Observable<Package[]> {
    return this.http.get<Package[]>(`${this.baseUrl}/packages`);
  }

  getActivePackages(): Observable<Package[]> {
    return this.http.get<Package[]>(`${this.baseUrl}/packages/active`);
  }

  searchPackagesByDestination(destination: string): Observable<Package[]> {
    return this.http.get<Package[]>(`${this.baseUrl}/packages/destination?destination=${destination}`);
  }

  searchPackagesByPrice(min?: number, max?: number): Observable<Package[]> {
    let params = '';
    if (min) params += `min=${min}&`;
    else params += `min=0&`;
    if (max) params += `max=${max}&`;
    else params += `max=1000000&`;
    
    return this.http.get<Package[]>(`${this.baseUrl}/packages/price?${params}`);
  }

  getHotelReviews(hotelId: number): Observable<Review[]> { 
  return this.http.get<Review[]>(`${this.baseUrl}/reviews/hotel/${hotelId}`);
}

createItinerary(itinerary: any): Observable<Itinerary> {
  const token = localStorage.getItem('authToken');
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
    return this.http.post<Itinerary>(`${this.baseUrl}/itineraries`, itinerary,{ headers });
  }
}
