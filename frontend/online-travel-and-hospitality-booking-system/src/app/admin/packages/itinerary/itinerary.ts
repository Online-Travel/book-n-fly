import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PackageService } from '../../services/package.service';
import { PackageNavbar } from '../package-navbar/package-navbar';

@Component({
  selector: 'app-itinerary',
  standalone:true,
  imports: [PackageNavbar,CommonModule,FormsModule],
  templateUrl: './itinerary.html',
  styleUrl: './itinerary.css'
})
export class Itinerary {

   itineraries: any[] = [];
  showForm = true;
  itineraryForm: any = {};

  constructor(private packageService: PackageService) {}

  ngOnInit(): void {
    this.loadItineraries();
    this.resetForm();
  }

  loadItineraries() {
    this.packageService.getAll().subscribe(data => {
      this.itineraries = data;
    });
  }

  onSubmit() {
    if (this.itineraryForm.itineraryId) {
      this.packageService.update(this.itineraryForm.itineraryId, this.itineraryForm).subscribe(() => {
        this.loadItineraries();
        this.resetForm();
      });
    } else {
      this.packageService.create(this.itineraryForm).subscribe(() => {
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
    this.packageService.delete(id).subscribe(() => {
      this.loadItineraries();
    });
  }

  resetForm() {
    this.itineraryForm = {
      itineraryId: null,
      packageId: '',
      customizationDetails: '',
      startDate: '',
      endDate: '',
      numberOfTravelers: '',
      status: 'DRAFT'
    };
  }

}
