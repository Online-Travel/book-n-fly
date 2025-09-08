import { Component, OnInit } from '@angular/core';
import { PaymentNavbar } from '../payment-navbar/payment-navbar';
import { CommonModule } from '@angular/common';
import { BookingPaymentResponseDTO } from '../../models/payment.model';
import { PaymentService } from '../../services/payment.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-booking-with-payments',
  standalone:true,
  imports: [PaymentNavbar,CommonModule,RouterModule],
  templateUrl: './booking-with-payments.html',
  styleUrls: ['./booking-with-payments.css']
})
export class BookingWithPayments implements OnInit {

constructor(private paymentService: PaymentService) {}

  allBookingsWithPayments: BookingPaymentResponseDTO[] = [];

loadAllBookingsWithPayments(): void {
  this.paymentService.getAllBookingWithPayments().subscribe({
    next: (data) => {
      this.allBookingsWithPayments = data;
      //console.log("Bookings with payments:", data);
      //console.log("all Booking "+this.allBookingsWithPayments);
    },
    error: (err) => console.error("Error loading booking with payments", err)
  });
}

// call this in ngOnInit
ngOnInit(): void {
  this.loadAllBookingsWithPayments();
}

}
