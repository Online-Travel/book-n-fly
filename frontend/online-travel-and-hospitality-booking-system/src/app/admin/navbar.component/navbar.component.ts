import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
   constructor(public authService: AuthService, private router: Router) {}

  goToPayment() {
    this.router.navigate(['/payment']);
  }

  goToUser(){
    this.router.navigate(['/user']);
  }

  goToPackage(){
    this.router.navigate(['/package']);
  }

  goToBooking() {
    this.router.navigate(['/bookings']);
  }

   goToReview() {
    this.router.navigate(['/review']);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
