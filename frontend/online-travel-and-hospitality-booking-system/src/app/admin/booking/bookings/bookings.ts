import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BookingService } from '../../services/booking.service';
import { Booking } from '../../models/booking.model';
import { BookingNavbar } from '../booking-navbar/booking-navbar';

@Component({
  selector: 'app-bookings',
  standalone:true,
  imports: [BookingNavbar,CommonModule,FormsModule],
  templateUrl: './bookings.html',
  styleUrl: './bookings.css'
})
export class Bookings {
  bookings: Booking[] = [];
  displayedBookings: Booking[] = [];
  selected?: Booking;
  bookingId: number | null = null;
  error: string | null = null;

  constructor(private bookingService: BookingService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.bookingService.getAllBookings().subscribe({
      next: (res) => {
        this.bookings = res;
        this.displayedBookings = res;
        this.error = null;
      },
      error: () => {
        this.error = 'Failed to load bookings';
      }
    });
  }

  searchBooking(): void {
    if (this.bookingId != null) {
      this.bookingService.getBookingById(this.bookingId).subscribe({
        next: (res) => {
          this.displayedBookings = [res]; // show only searched booking
          this.error = null;
        },
        error: () => {
          this.displayedBookings = [];
          this.error = 'Booking not found';
        }
      });
    }
  }

  resetSearch(): void {
    this.bookingId = null;
    this.displayedBookings = this.bookings; // restore full list
    this.error = null;
  }

  view(id?: number): void {
    if (!id) return;
    this.bookingService.getBookingById(id).subscribe({
      next: (res) => {
        this.selected = res;
        this.error = null;
      },
      error: () => {
        this.selected = undefined;
        this.error = 'Booking not found';
      }
    });
  }
}
