import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class HotelService {
  private apiUrl = 'http://localhost:8080/api/hotels'; // adjust gateway path

  constructor(private http: HttpClient) {}

  getHotels() {
    return this.http.get(this.apiUrl);
  }
}
