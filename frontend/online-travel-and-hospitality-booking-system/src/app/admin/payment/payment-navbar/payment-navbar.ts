import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-payment-navbar',
  standalone: true,
  imports: [RouterLink,CommonModule],
  templateUrl: './payment-navbar.html',
  styleUrl: './payment-navbar.css'
})
export class PaymentNavbar {

}
