import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class FlightService {
  private apiUrl = 'http://localhost:8080/api/flights'; // adjust gateway path

  constructor(private http: HttpClient) {}

  getFlights() {
    return this.http.get(this.apiUrl);
  }
}
