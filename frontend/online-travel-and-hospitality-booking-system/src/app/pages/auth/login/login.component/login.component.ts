import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../../auth/auth.service';
import { LoginRequest } from '../../../../auth/auth.model';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-login.component',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  loading = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
    // Redirect if already logged in
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/user']);
    }
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.loading = true;
      this.error = '';

      const credentials: LoginRequest = this.loginForm.value;

      this.authService.login(credentials).subscribe({
        next: (response) => {
          this.authService.setAuth(response);
          this.loading = false;
          console.log('Login successful', response);
          if(response.role==='ADMIN'){
            this.router.navigate(['/admin/user'])
          }
          else if(response.role==='TRAVELER'){
            console.log("traveler")
            this.router.navigate(['/traveller'])
          }
          else if(response.role==='TRAVEL_AGENT'){
            this.router.navigate(['/agent/packages'])
          }
          else if(response.role==='HOTEL_MANAGER'){
            this.router.navigate(['/hotel-manager'])
          }
          else{
          this.router.navigate(['/login']);
          } // Redirect after login
        },
        error: (err) => {
          this.loading = false;
          this.error = err.status === 401 
            ? 'Invalid email or password' 
            : 'Login failed. Please try again.';
        }
      });
    }
  }

  // Helper for form controls
  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }
}
