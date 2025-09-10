import { Component, OnInit } from '@angular/core';
import { HotelManagerNavbar } from "../../components/hotel-manager-navbar/hotel-manager-navbar";
import { Hotel } from '../../../admin/models/booking.model';
import { HotelManager } from '../../services/hotel-manager';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-hotel-manager-dashboard',
  standalone:true,
  imports: [CommonModule,HotelManagerNavbar,ReactiveFormsModule,FormsModule],
  templateUrl: './hotel-manager-dashboard.html',
  styleUrls: ['./hotel-manager-dashboard.css']
})
export class HotelManagerDashboard implements OnInit {

  hotels: Hotel[] = [];
  newHotel: Hotel = { name: '', location: '', roomsAvailable: 0, rating: 0, pricePerNight: 0 };
  editingHotel: Hotel | null = null;

  constructor(private hotelService: HotelManager) { }

  ngOnInit(): void {
    this.loadHotels();
  }

  loadHotels(): void {
    this.hotelService.getMyHotels().subscribe({
      next: (data) => this.hotels = data,
      error: (err) => console.error(err)
    });
  }

  addHotel(): void {
    this.hotelService.createHotel(this.newHotel).subscribe({
      next: (hotel) => {
        this.hotels.push(hotel);
        this.newHotel = { name: '', location: '', roomsAvailable: 0, rating: 0, pricePerNight: 0 };
      },
      error: (err) => console.error(err)
    });
  }

  editHotel(hotel: Hotel): void {
    this.editingHotel = { ...hotel };
  }

  updateHotel(): void {
    if (this.editingHotel && this.editingHotel.hotelId) {
      this.hotelService.updateHotel(this.editingHotel.hotelId, this.editingHotel).subscribe({
        next: (updated) => {
          const index = this.hotels.findIndex(h => h.hotelId === updated.hotelId);
          if (index !== -1) this.hotels[index] = updated;
          this.editingHotel = null;
        },
        error: (err) => console.error(err)
      });
    }
  }

  cancelEdit(): void {
    this.editingHotel = null;
  }
}
