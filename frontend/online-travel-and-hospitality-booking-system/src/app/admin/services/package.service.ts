import { Injectable } from '@angular/core';
import {HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PackageService {
  private flightUrl = 'http://localhost:8080/api/flights';

  private hotelUrl = 'http://localhost:8080/api/hotels';

  private packageUrl = 'http://localhost:8080/api/packages'

  private itineraryUrl = 'http://localhost:8080/api/itineraries'

  constructor(private http: HttpClient) {}

  getFlights() {
    return this.http.get(this.flightUrl);
  }

   getHotels() {
    return this.http.get(this.hotelUrl);
  }

  addPackage(pkg: any) {
    return this.http.post(this.packageUrl, pkg, this.authHeader());
  }

  updatePackage(id: number, pkg: any) {
    return this.http.put(`${this.packageUrl}/${id}`, pkg, this.authHeader());
  }

  softDeletePackage(id: number) {
    return this.http.patch(`${this.packageUrl}/${id}/inactive`, {}, this.authHeader());
  }

  makePackageActive(id: number) {
    return this.http.patch(`${this.packageUrl}/${id}/active`, {}, this.authHeader());
  }

  hardDelete(id: number) {
    return this.http.delete(`${this.packageUrl}/${id}/hard`, this.authHeader());
  }

  getPackages() {
    return this.http.get(this.packageUrl);
  }

  getActivePackages() {
    return this.http.get(`${this.packageUrl}/active`);
  }

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.itineraryUrl);
  }

  create(itinerary: any): Observable<any> {
    return this.http.post(this.itineraryUrl, itinerary);
  }

  update(id: number, itinerary: any): Observable<any> {
    return this.http.put(`${this.itineraryUrl}/${id}`, itinerary);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.itineraryUrl}/${id}`);
  }

  private authHeader() {
    const token = localStorage.getItem('token');
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
    };
  }
}