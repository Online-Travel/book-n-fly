import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PackageService } from '../../services/package';
import { HotelService } from '../../services/hotel';
import { FlightService } from '../../services/flight';
import { AuthService } from '../../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './packages.html'
})
export class PackageComponent implements OnInit {
  packages: any[] = [];
  hotels: any[] = [];
  flights: any[] = [];

  packageForm: any = {
    packageId: null,
    name: '',
    destination: '',
    description: '',
    price: null,
    durationDays: null,
    createdByAgentId: 1,
    activities: [],
    includedHotelIds: [],
    includedFlightIds: [],
    isActive: true
  };

  activitiesInput: string = "";
  showForm: boolean = true;

  constructor(
    private packageService: PackageService,
    private hotelService: HotelService,
    private flightService: FlightService,
    public auth: AuthService,
    private router: Router   
  ) {}

  ngOnInit(): void {
    this.loadPackages();
    this.loadHotels();
    this.loadFlights();
  }

  loadPackages() {
    this.packageService.getPackages().subscribe((data: any) => {
      this.packages = data;
    });
  }

  loadHotels() {
    this.hotelService.getHotels().subscribe((data: any) => {
      this.hotels = data;
    });
  }

  loadFlights() {
    this.flightService.getFlights().subscribe((data: any) => {
      this.flights = data;
    });
  }
sshowForm = true;

logout() {
  localStorage.removeItem('token');
  this.router.navigate(['/login']);  // <-- use router from component
}

onSubmit() {
  this.packageForm.activities = this.activitiesInput
    ? this.activitiesInput.split(',').map(a => a.trim())
    : [];

  if (!this.packageForm.description) this.packageForm.description = '';

  if (this.packageForm.packageId) {
    // Update
    this.packageService.updatePackage(this.packageForm.packageId, this.packageForm)
      .subscribe({
        next: () => {
          alert('Package updated successfully!');
          const index = this.packages.findIndex(p => p.packageId === this.packageForm.packageId);
          if (index !== -1) this.packages[index] = {...this.packageForm};
          this.resetForm();
        },
        error: err => console.error(err)
      });
  } else {
    // Add
    this.packageService.addPackage(this.packageForm)
      .subscribe({
        next: (res: any) => {
          alert('Package added successfully!');
          this.packages.push(res); // Add newly created package
          this.resetForm();
        },
        error: err => console.error(err)
      });
  }
}

editPackage(pkg: any) {
  this.showForm = true;
  this.packageForm = {
    packageId: pkg.packageId,
    name: pkg.name || '',
    destination: pkg.destination || '',
    description: pkg.description || '',
    price: pkg.price || null,
    durationDays: pkg.durationDays || null,
    createdByAgentId: pkg.createdByAgentId || 1,
    activities: pkg.activities || [],
    includedHotelIds: pkg.includedHotelIds || [],
    includedFlightIds: pkg.includedFlightIds || [],
    isActive: pkg.isActive
  };
  this.activitiesInput = pkg.activities ? pkg.activities.join(', ') : '';
}


softDelete(id: number) {
  this.packageService.softDeletePackage(id).subscribe({
    next: () => {
      const pkg = this.packages.find(p => p.packageId === id);
      if (pkg) pkg.isActive = false; // Update UI immediately
    },
    error: err => {
      console.error('Failed to make package inactive', err);
      alert('Failed to make package inactive.');
    }
  });
}

makeActive(id: number) {
  this.packageService.makePackageActive(id).subscribe({
    next: () => {
      const pkg = this.packages.find(p => p.packageId === id);
      if (pkg) pkg.isActive = true; // Update UI immediately
    },
    error: err => {
      console.error('Failed to make package active', err);
      alert('Failed to make package active.');
    }
  });
}

  resetForm() {
    this.packageForm = {
      packageId: null,
      name: '',
      destination: '',
      description: '',
      price: null,
      durationDays: null,
      createdByAgentId: 1,
      activities: [],
      includedHotelIds: [],
      includedFlightIds: [],
      isActive: true
    };
    this.activitiesInput = "";
  }
}
