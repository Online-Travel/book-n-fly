import { Component, OnInit } from '@angular/core';
import { PaymentNavbar } from '../payment-navbar/payment-navbar';
import { CommonModule } from '@angular/common';
import { UserPaymentResponseDTO } from '../../models/payment.model';
import { PaymentService } from '../../services/payment.service';


@Component({
  selector: 'app-user-with-payments',
  standalone:true,
  imports: [PaymentNavbar,CommonModule],
  templateUrl: './user-with-payments.html',
  styleUrls: ['./user-with-payments.css']
})
export class UserWithPayments implements OnInit {
    allUsersWithPayments: UserPaymentResponseDTO[] = [];

  constructor(private paymentService: PaymentService) {}

  ngOnInit(): void {
    this.loadAllUsersWithPayments();
  }

  loadAllUsersWithPayments(): void {
    this.paymentService.getAllUserWithPayments().subscribe({
      next: (data) => {
        this.allUsersWithPayments = data;
        console.log("Users with payments:", this.allUsersWithPayments);
      },
      error: (err) => console.error("Error loading users with payments", err)
    });
  }
}
