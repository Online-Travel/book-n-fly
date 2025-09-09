import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8081/user/login'; // backend login API

  constructor(private http: HttpClient, private router: Router) {}

  // Login and automatically replace token, role, and userId in localStorage
  login(credentials: { email: string; password: string }) {
    return this.http.post<{ token: string; role: string; userId: number }>(
      this.apiUrl,
      credentials
    ).pipe(
      tap(res => {
        // ✅ Always remove old data before saving
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        localStorage.removeItem('userId');

        // ✅ Save fresh token and details
        localStorage.setItem('token', res.token);
        localStorage.setItem('role', res.role);
        localStorage.setItem('userId', res.userId.toString());
      })
    );
  }

  getToken() { return localStorage.getItem('token'); }
  isLoggedIn() { return !!this.getToken(); }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  getUserId(): number | null {
    const id = localStorage.getItem('userId');
    return id ? Number(id) : null;
  }

  // Logout = delete everything
  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('userId');
    this.router.navigate(['/login']);
  }
}
