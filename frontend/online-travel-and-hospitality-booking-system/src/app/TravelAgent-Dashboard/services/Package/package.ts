import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../../../auth/auth.service';

@Injectable({ providedIn: 'root' })
export class PackageService {
  private apiUrl = 'http://localhost:8080/api/packages';

  constructor(private http: HttpClient,private authService:AuthService) {}

  addPackage(pkg: any) {
    return this.http.post(this.apiUrl, pkg, this.authHeader());
  }

  updatePackage(id: number, pkg: any) {
    return this.http.put(`${this.apiUrl}/${id}`, pkg, this.authHeader());
  }

  softDeletePackage(id: number) {
    return this.http.patch(`${this.apiUrl}/${id}/inactive`, {}, this.authHeader());
  }

  makePackageActive(id: number) {
    return this.http.patch(`${this.apiUrl}/${id}/active`, {}, this.authHeader());
  }

  hardDeletePackage(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}/hard`, this.authHeader());
  }
 getPackagesByDestination(destination: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/destination/${destination}`, this.authHeader() );
  }

  /** Admin: get all packages */
  getPackages() {
    return this.http.get(this.apiUrl, this.authHeader());
  }
// package.service.ts

  /** Travel Agent: get only their own packages */
  getMyPackages() {
    return this.http.get(`${this.apiUrl}/my-packages`, this.authHeader());
  }

  getActivePackages() {
    return this.http.get(`${this.apiUrl}/active`, this.authHeader());
  }

  private authHeader() {
    const token = this.authService.getToken();
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
    };
  }
}
