import { Component, inject } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-hotel-manager-navbar',
  imports: [RouterLink],
  templateUrl: './hotel-manager-navbar.html',
  styleUrl: './hotel-manager-navbar.css'
})
export class HotelManagerNavbar {
  authService: AuthService = inject(AuthService);
  router: Router = inject(Router);

  logout(): void {
    this.authService.logout();
    this.router.navigate(["/"]);
  }
}
