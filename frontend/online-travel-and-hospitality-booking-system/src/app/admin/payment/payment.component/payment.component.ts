import { Component, OnInit } from '@angular/core';
import { PaymentNavbar } from '../payment-navbar/payment-navbar';
import { CommonModule } from '@angular/common';
//import { RouterOutlet } from '@angular/router';
import { PaymentResponseDTO } from '../../models/payment.model';
import { PaymentService } from '../../services/payment.service';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

declare var bootstrap: any;

@Component({
  selector: 'app-payment.component',
  imports: [PaymentNavbar,CommonModule,FormsModule],
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit{

  payments: PaymentResponseDTO[] = [];
  loading: boolean = true;
  error: string | null = null;

  constructor(private paymentService: PaymentService) {}

  userIdFilter: number | null = null;
  paymentIdFilter: number | null = null;
  bookingIdFilter: number | null = null;
  currentFilter: 'all' | 'user' | 'payment' | 'booking' = 'all';

   selectedPaymentId: number | null = null;
  updateStatus: string = 'PENDING';
  updateMethod: string = 'CARD';
  modal: any;

  ngOnInit(): void {
    this.loadPayments();
  }

  loadPayments(): void {
    this.paymentService.getAllPayments().subscribe({
      next: (data) => {
        this.payments = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load payments';
        this.loading = false;
        console.error(err);
      }
    });
  }

   filterByUserId(): void {
    if (this.userIdFilter != null) {
      this.currentFilter = 'user';
      this.loading = true;
      this.error = null;
      
      this.paymentService.getPaymentsByUserId(this.userIdFilter).subscribe({
        next: (data) => {
          this.payments = Array.isArray(data) ? data : [data];
          this.loading = false;
        },
        error: (err: HttpErrorResponse) => {
          this.loading = false;
          if (err.status === 404) {
            this.payments = [];
            this.error = null;
          } else {
            this.error = 'Failed to load payments for this user';
          }
        }
      });
    } else {
      this.loadPayments();
    }
  }

  filterByPaymentId(): void {
    if (this.paymentIdFilter != null) {
      this.currentFilter = 'payment';
      this.loading = true;
      this.error = null;
      
      this.paymentService.getPaymentByPaymentId(this.paymentIdFilter).subscribe({
        next: (data) => {
          this.payments = [data];
          this.loading = false;
        },
        error: (err: HttpErrorResponse) => {
          this.loading = false;
          if (err.status === 404) {
            this.payments = [];
            this.error = null;
          } else {
            this.error = 'Failed to load payment';
          }
        }
      });
    } else {
      this.loadPayments();
    }
  }

  filterByBookingId(): void {
    if (this.bookingIdFilter != null) {
      this.currentFilter = 'booking';
      this.loading = true;
      this.error = null;
      
      this.paymentService.getPaymentByBookingId(this.bookingIdFilter).subscribe({
        next: (data) => {
          this.payments = [data];
          this.loading = false;
        },
        error: (err: HttpErrorResponse) => {
          this.loading = false;
          if (err.status === 404) {
            this.payments = [];
            this.error = null;
          } else {
            this.error = 'Failed to load payment for this booking';
          }
        }
      });
    } else {
      this.loadPayments();
    }
  }

  resetFilters(): void {
    this.userIdFilter = null;
    this.paymentIdFilter = null;
    this.bookingIdFilter = null;
    this.currentFilter = 'all';
    this.error = null;
    this.loadPayments();
  }

  openUpdateModal(payment: PaymentResponseDTO): void {
    this.selectedPaymentId = payment.paymentId;
    this.updateStatus = payment.status;
    this.updateMethod = payment.paymentMethod;

    const modalEl = document.getElementById('updatePaymentModal');
    this.modal = new bootstrap.Modal(modalEl!);
    this.modal.show();
  }

  confirmUpdate(): void {
    if (this.selectedPaymentId !== null) {
      this.paymentService.updatePayment(this.selectedPaymentId, {
        status: this.updateStatus,
        paymentMethod: this.updateMethod
      }).subscribe({
        next: (updated) => {
          this.payments = this.payments.map(p =>
            p.paymentId === updated.paymentId ? updated : p
          );
          this.modal.hide();
        },
        error: () => {
          this.error = 'Failed to update payment';
        }
      });
    }
  }
// updatePayment(paymentId: number, newStatus: string, newMethod: string): void {
//     const updateReq = { status: newStatus, paymentMethod: newMethod };
//     this.paymentService.updatePayment(paymentId, updateReq).subscribe({
//       next: (updated) => {
//         alert(`Payment ${paymentId} updated successfully!`);
//         this.loadPayments(); // refresh table
//       },
//       error: (err) => console.error('Update failed', err)
//     });
//   }

  // ✅ Delete a payment
  deletePayment(paymentId: number): void {
    if (confirm('Are you sure you want to delete this payment?')) {
      this.paymentService.deletePayment(paymentId).subscribe({
        next: () => {
          alert(`Payment ${paymentId} deleted successfully!`);
          this.loadPayments(); // refresh table
        },
        error: (err) => console.error('Delete failed', err)
      });
    }
  }
}
