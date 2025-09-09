import { Component } from '@angular/core';
import { BookingNavbar } from '../booking-navbar/booking-navbar';
import { CommonModule } from '@angular/common';
import { BookingService } from '../../services/booking.service';
import { Flight } from '../../models/booking.model';
import { OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-flights',
  standalone:true,
  imports: [BookingNavbar,CommonModule,FormsModule],
  templateUrl: './flights.html',
  styleUrls: ['./flights.css']
})
export class Flights implements OnInit{
  flights: Flight[] = [];
  displayedFlights: Flight[] = [];   // 👈 for search results
  selected?: Flight;
  flightId: number | null = null;    // 👈 search input
  error: string | null = null;

  newFlight: Partial<Flight> = { 
    airline: '', 
    departure: '', 
    arrival: '', 
    price: 0, 
    availability: true 
  };

  constructor(private bookingService: BookingService) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.bookingService.getAllFlights().subscribe(res => {
      this.flights = res;
      this.displayedFlights = res; // 👈 show all initially
    });
  }

  searchFlight(): void {
    if (this.flightId != null) {
      this.bookingService.getFlightById(this.flightId).subscribe({
        next: (res) => {
          if (res) {
            this.displayedFlights = [res]; // single wrapped in array
            this.error = null;
          } else {
            this.displayedFlights = [];
            this.error = 'Flight not found';
          }
        },
        error: () => {
          this.displayedFlights = [];
          this.error = 'Flight not found';
        }
      });
    }
  }

  resetSearch(): void {
    this.flightId = null;
    this.displayedFlights = this.flights; // restore all
    this.error = null;
  }

  view(id: number): void {
    this.bookingService.getFlightById(id).subscribe(res => this.selected = res);
  }

  create(): void {
    this.bookingService.createFlight(this.newFlight as Flight).subscribe(() => {
      this.newFlight = { airline: '', departure: '', arrival: '', price: 0, availability: true };
      this.load();
    });
  }

  update(f: Flight): void {
    this.bookingService.updateFlight(f.flightId!, f).subscribe(() => this.load());
  }

  remove(id: number): void {
    this.bookingService.deleteFlight(id).subscribe(() => this.load());
  }
}
