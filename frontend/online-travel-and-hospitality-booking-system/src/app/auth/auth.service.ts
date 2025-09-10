

import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { LoginRequest, AuthResponse } from './auth.model';
import { isPlatformBrowser } from '@angular/common';

export interface SignupRequest {
  name: string;
  email: string;
  password: string;
  role: string;
  contactNumber: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/user/login';
  private currentUserSubject = new BehaviorSubject<AuthResponse | null>(null);
  private isBrowser: boolean;

  private baseUrl = 'http://localhost:8080/user';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);

    // Only access localStorage in browser
    if (this.isBrowser) {
      const saved = localStorage.getItem('auth');
      if (saved) {
        this.currentUserSubject.next(JSON.parse(saved));
      }
    }
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(this.apiUrl, credentials);
  }

  signup(user: SignupRequest): Observable<string> {
    return this.http.post(`${this.baseUrl}/adduser`, user, { responseType: 'text' });
  }

  // Save token and role
  setAuth(auth: AuthResponse): void {
    if (this.isBrowser) {
      localStorage.setItem('auth', JSON.stringify(auth));
    }
    this.currentUserSubject.next(auth);
  }

  // Get current user data
  getAuth(): AuthResponse | null {
    return this.currentUserSubject.value;
  }

  // Get token only (for API calls)
  getToken(): string | null {
    const auth = this.getAuth();
    return auth ? auth.token : null;
  }

  // Check if user is logged in
  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  // Get user role
  getRole(): string | null {
    const auth = this.getAuth();
    return auth ? auth.role : null;
  }

  // Logout
  logout(): void {
    if (this.isBrowser) {
      localStorage.removeItem('auth');
    }
    this.currentUserSubject.next(null);
  }
  
  // Map roles to dashboard routes - make sure this matches your actual routes
  getDashboardRouteForRole(role: string | null): string | null {
    switch (role?.toLowerCase()) {
      case 'admin': return '/admin';
      case 'traveller': return '/traveller';
      case 'travel agent': return '/agent';
      case 'hotel manager': return '/hotel-manager';
      default: return null; // Unknown role
    }
  }
  
}
