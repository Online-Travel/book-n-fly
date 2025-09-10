import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html'
})
export class LoginComponent {
  credentials = { email: '', password: '' };
  error = '';

  constructor(private auth: AuthService, private router: Router) {}

  login() {
  console.log('Sending credentials:', this.credentials);
  this.auth.login(this.credentials).subscribe({
    next: (res) => {
      console.log('Response from backend:', res);
      if (res.role === 'TRAVELER') {
        this.auth.saveToken(res.token);
        this.auth.saveUserEmail(this.credentials.email);
        this.router.navigate(['/traveller/board']);
      } else {
        this.error = 'Access denied. Only Travel Agents allowed.';
      }
    },
    error: (err) => {
      console.error('Error from backend:', err);
      this.error = 'Invalid credentials';
    }
  });
}
}

