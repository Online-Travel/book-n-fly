import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { RouterLinkActive } from '@angular/router';


@Component({
  selector: 'app-booking-navbar',
  standalone:true,
  imports: [CommonModule,RouterLink,RouterLinkActive],
  templateUrl: './booking-navbar.html',
  styleUrls: ['./booking-navbar.css']
})
export class BookingNavbar {

}
