import { Component } from '@angular/core';
import { PaymentNavbar } from '../payment-navbar/payment-navbar';
import { CommonModule } from '@angular/common';
import { InvoiceResponseDTO } from '../../models/payment.model';
import { PaymentService } from '../../services/payment.service';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-invoices',
  standalone:true,
  imports: [PaymentNavbar,CommonModule,FormsModule],
  templateUrl: './invoices.html',
  styleUrls: ['./invoices.css']
})
export class Invoices {
  invoices: InvoiceResponseDTO[] = [];
  loading: boolean = false;
  errorMessage: string = '';

  userIdFilter: number | null = null;
  bookingIdFilter: number | null = null;

  constructor(private paymentService: PaymentService) {}

  ngOnInit(): void {
    this.fetchInvoices();
  }

  fetchInvoices() {

    this.loading = true;
    this.paymentService.getAllInvoices().subscribe({
      next: (data) => {
        this.invoices = data.map(inv => ({
          ...inv,
          timestamp: new Date(inv.timestamp)  // convert string to Date
        }));
        this.loading = false;
      },
      error: (err: HttpErrorResponse) => {
        this.errorMessage = err.message;
        this.loading = false;
      }
    });
  }

  deleteInvoice(invoiceId: number) {
    if (confirm('Are you sure you want to delete this invoice?')) {
      this.paymentService.deleteInvoice(invoiceId).subscribe({
        next: () => {
          this.invoices = this.invoices.filter(inv => inv.invoiceId !== invoiceId);
        },
        error: (err: HttpErrorResponse) => {
          alert('Failed to delete invoice: ' + err.message);
        }
      });
    }
  }
  filterByUser() {
  if (this.userIdFilter != null) {
    this.loading = true;
    this.errorMessage = ''; // Clear previous errors
    this.paymentService.getInvoiceByUserId(this.userIdFilter).subscribe({
      next: (data: InvoiceResponseDTO[]) => {
        this.invoices = data.map((inv: InvoiceResponseDTO) => ({
          ...inv,
          timestamp: new Date(inv.timestamp)
        }));
        this.loading = false;
      },
      error: (err: HttpErrorResponse) => {
        this.loading = false;
        if (err.status === 404) {
          // Handle 404 as no data found, not an error
          this.invoices = [];
          this.errorMessage = '';
        } else {
          // Handle other actual errors
          this.errorMessage = err.message;
        }
      }
    });
  } else {
    this.fetchInvoices();
  }
}

filterByBooking() {
  if (this.bookingIdFilter != null) {
    this.loading = true;
    this.errorMessage = ''; // Clear previous errors
    this.paymentService.getInvoiceByBookingId(this.bookingIdFilter).subscribe({
      next: (data: InvoiceResponseDTO[]) => {
        this.invoices = data.map((inv: InvoiceResponseDTO) => ({
          ...inv,
          timestamp: new Date(inv.timestamp)
        }));
        this.loading = false;
      },
      error: (err: HttpErrorResponse) => {
        this.loading = false;
        if (err.status === 404) {
          // Handle 404 as no data found, not an error
          this.invoices = [];
          this.errorMessage = '';
        } else {
          // Handle other actual errors
          this.errorMessage = err.message;
        }
      }
    });
  } else {
    this.fetchInvoices();
  }
}



resetUserFilter() {
 this.userIdFilter = null;
  this.bookingIdFilter = null;
  this.errorMessage = '';
  this.fetchInvoices(); // optionally reload all invoices
}

}
