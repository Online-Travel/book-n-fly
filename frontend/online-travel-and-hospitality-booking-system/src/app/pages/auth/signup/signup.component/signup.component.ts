import { Component } from '@angular/core';
import { AuthService } from '../../../../auth/auth.service';
import { SignupRequest } from '../../../../auth/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup.component',
  standalone:true,
  imports: [CommonModule,FormsModule],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
   user: SignupRequest = {
    name: '',
    email: '',
    password: '',
    role: 'Traveler', // default role
    contactNumber:  null as any
  };

  message: string = '';

  constructor(private authService: AuthService,private router:Router) {}

  onSignup(): void {
    this.authService.signup(this.user).subscribe({
      next: (res) => {
        this.message = 'User registered successfully!';
        this.user = { name: '', email: '', password: '', role: 'Traveler', contactNumber: 0 };
        this.router.navigate(['/login']);
        
      },
      error: (err) => {
        console.error(err);
        this.message = 'Signup failed. Try again!';
      }
    });
  }
}
