import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NavbarComponent } from "../../navbar.component/navbar.component";

@Component({
  selector: 'app-payment-navbar',
  standalone: true,
  imports: [RouterLink, CommonModule, NavbarComponent],
  templateUrl: './payment-navbar.html',
  styleUrl: './payment-navbar.css'
})
export class PaymentNavbar {

}
