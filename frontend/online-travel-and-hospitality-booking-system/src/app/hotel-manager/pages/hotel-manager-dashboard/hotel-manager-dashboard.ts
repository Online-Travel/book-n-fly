import { Component, OnInit } from '@angular/core';
import { HotelManagerNavbar } from "../../components/hotel-manager-navbar/hotel-manager-navbar";
import { Hotel } from '../../../admin/models/booking.model';
import { HotelManager } from '../../services/hotel-manager';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-hotel-manager-dashboard',
  imports: [HotelManagerNavbar, FormsModule],
  templateUrl: './hotel-manager-dashboard.html',
  styleUrl: './hotel-manager-dashboard.css'
})
export class HotelManagerDashboard implements OnInit {
  hotels: Hotel[] = [];
  currentHotel: Hotel = this.getEmptyHotel();
  isEditMode = false;

  constructor(private hotelManager: HotelManager) { }

  ngOnInit(): void {
    this.loadHotels();
  }

  loadHotels(): void {
    this.hotelManager.getMyHotels().subscribe(
      (data: Hotel[]) => {
        this.hotels = data;
        console.log('Hotels loaded:', this.hotels);
      },
      (error) => {
        console.error('Error loading hotels:', error);
        // Handle error appropriately (show message to user)
        console.error('Error loading hotels:', error);
        alert('Error loading hotels');
      }
    );
  }

  editHotel(hotel: Hotel): void {
    this.currentHotel = { ...hotel };
    this.isEditMode = true;
  }

  createNewHotel(): void {
    this.currentHotel = this.getEmptyHotel();
    this.isEditMode = false;
  }

  onSubmit(): void {
    if (this.isEditMode) {
      this.hotelManager.updateHotel(this.currentHotel.hotelId!, this.currentHotel).subscribe(
        (updatedHotel: Hotel) => {
          this.loadHotels(); // Reload to show updated data
          alert('Hotel updated successfully!');
        },
        (error) => {
          console.error('Error updating hotel:', error);
          alert('Error updating hotel');
        }
      );
    } else {
      console.log('Creating new hotel with data:', this.currentHotel);
      this.hotelManager.createHotel(this.currentHotel).subscribe(
        (newHotel) => {
          this.loadHotels(); // Reload to show new hotel
          alert('Hotel created successfully!');
        },
        (error) => {
          console.error('Error creating hotel:', error);
          alert('Error creating hotel');
        }
      );
    }
  }

  private getEmptyHotel(): Hotel {
    return {
      hotelId: 0,
      name: '',
      location: '',
      roomsAvailable: 0,
      rating: 0,
      pricePerNight: 0,
    };
  }
}
