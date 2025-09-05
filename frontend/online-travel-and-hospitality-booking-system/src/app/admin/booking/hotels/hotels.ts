import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { BookingNavbar } from '../booking-navbar/booking-navbar';
import { BookingService } from '../../services/booking.service';
import { Hotel } from '../../models/booking.model';
import { FormsModule } from '@angular/forms';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-hotels',
  standalone:true,
  imports: [CommonModule,BookingNavbar,FormsModule],
  templateUrl: './hotels.html',
  styleUrls: ['./hotels.css']
})
export class Hotels implements OnInit {
  hotels: Hotel[] = [];
  displayedHotels: Hotel[] = [];   // 👈 for list vs search
  selected?: Hotel;
  hotelId: number | null = null;   // 👈 for search
  error: string | null = null;

  constructor(private bookingService: BookingService) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.bookingService.getAllHotels().subscribe(res => {
      this.hotels = res;
      this.displayedHotels = res; // 👈 show all initially
    });
  }

  searchHotel(): void {
    if (this.hotelId != null) {
      this.bookingService.getHotelById(this.hotelId).subscribe({
        next: (res) => {
          if (res) {
            this.displayedHotels = [res]; // wrap single in array
            this.error = null;
          } else {
            this.displayedHotels = [];
            this.error = 'Hotel not found';
          }
        },
        error: () => {
          this.displayedHotels = [];
          this.error = 'Hotel not found';
        }
      });
    }
  }

  resetSearch(): void {
    this.hotelId = null;
    this.displayedHotels = this.hotels; // restore all
    this.error = null;
  }

  view(id: number): void {
    this.bookingService.getHotelById(id).subscribe(res => this.selected = res);
  }

  remove(id: number): void {
    this.bookingService.deleteHotel(id).subscribe(() => this.load());
  }
}
