import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Router} from "@angular/router";
@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('online-travel-and-hospitality-booking-system');
  constructor(private router: Router) {}

  goToLogin() {
    this.router.navigate(['/login']);
  }

  goToSignUp() {
    this.router.navigate(['/sign-up']);
  }
}
