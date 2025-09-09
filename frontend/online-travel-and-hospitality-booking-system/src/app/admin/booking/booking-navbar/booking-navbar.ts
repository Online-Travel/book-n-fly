import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { RouterLinkActive } from '@angular/router';
import { NavbarComponent } from "../../navbar.component/navbar.component";


@Component({
  selector: 'app-booking-navbar',
  standalone:true,
  imports: [CommonModule, RouterLink, RouterLinkActive, NavbarComponent],
  templateUrl: './booking-navbar.html',
  styleUrls: ['./booking-navbar.css']
})
export class BookingNavbar {

}
