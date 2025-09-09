import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ItineraryService } from '../../services/Itinerary/itinerary';
import { AuthService } from '../../services/Auth/auth';

@Component({
  selector: 'app-itineraries',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './itineraries.html'
})
export class ItinerariesComponent implements OnInit {
  itineraries: any[] = [];
  showForm = true;
  itineraryForm: any = {};

  constructor(
    private itineraryService: ItineraryService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadItineraries();
    this.resetForm();
  }

  loadItineraries() {
    const role = this.authService.getRole();
    const userId = this.authService.getUserId();

    if (role === 'ADMIN') {
      this.itineraryService.getAll().subscribe(data => {
        this.itineraries = data;
      });
    } else if (role === 'TRAVEL_AGENT') {
      this.itineraryService.getAgentItineraries().subscribe(data => {
        this.itineraries = data;
      });
    } else if (role === 'TRAVELER' && userId) {
      this.itineraryService.getUserItineraries(userId).subscribe(data => {
        this.itineraries = data;
      });
    }
  }

  onSubmit() {
    if (this.itineraryForm.itineraryId) {
      this.itineraryService
        .update(this.itineraryForm.itineraryId, this.itineraryForm)
        .subscribe(() => {
          this.loadItineraries();
          this.resetForm();
        });
    } else {
      this.itineraryService.create(this.itineraryForm).subscribe(() => {
        this.loadItineraries();
        this.resetForm();
      });
    }
  }

  editItinerary(it: any) {
    this.itineraryForm = { ...it };
    this.showForm = true;
  }

  deleteItinerary(id: number) {
    this.itineraryService.delete(id).subscribe(() => {
      this.loadItineraries();
    });
  }

  resetForm() {
    this.itineraryForm = {
      itineraryId: null,
      userId: null,
      packageId: '',
      customizationDetails: '',
      startDate: '',
      endDate: '',
      numberOfTravelers: '',
      status: 'DRAFT'
    };
  }
}
