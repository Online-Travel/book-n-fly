import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8081/user/login'; // match Postman URL

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: { email: string; password: string }) {
    return this.http.post<{ token: string; role: string }>(
      this.apiUrl,   // no extra /login here
      credentials
    );
  }

  saveToken(token: string) { localStorage.setItem('token', token); }
  getToken() { return localStorage.getItem('token'); }
  isLoggedIn() { return !!this.getToken(); }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}

