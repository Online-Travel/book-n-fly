import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../auth/auth.service';
import { PackageService } from '../../services/package.service';
import { Router } from '@angular/router';
import { PackageNavbar } from '../package-navbar/package-navbar';


@Component({
  selector: 'app-package',
  standalone:true,
  imports: [PackageNavbar,CommonModule,FormsModule],
  templateUrl: './package.html',
  styleUrl: './package.css'
})
export class Package {
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
    private hotelService: PackageService,
    private flightService: PackageService,
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

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  onSubmit() {
    this.packageForm.activities = this.activitiesInput
      ? this.activitiesInput.split(',').map(a => a.trim())
      : [];

    if (!this.packageForm.description) this.packageForm.description = '';

    if (this.packageForm.packageId) {
      this.packageService.updatePackage(this.packageForm.packageId, this.packageForm).subscribe({
        next: () => {
          alert('Package updated successfully!');
          const index = this.packages.findIndex(p => p.packageId === this.packageForm.packageId);
          if (index !== -1) this.packages[index] = { ...this.packageForm };
          this.resetForm();
        },
        error: err => console.error(err)
      });
    } else {
      this.packageService.addPackage(this.packageForm).subscribe({
        next: (res: any) => {
          alert('Package added successfully!');
          this.packages.push(res);
          this.resetForm();
        },
        error: err => console.error(err)
      });
    }
  }

  editPackage(pkg: any) {
    this.showForm = true;
    this.packageForm = { ...pkg };
    this.activitiesInput = pkg.activities ? pkg.activities.join(', ') : '';
  }

  softDelete(id: number) {
    this.packageService.softDeletePackage(id).subscribe({
      next: () => {
        const pkg = this.packages.find(p => p.packageId === id);
        if (pkg) pkg.isActive = false;
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
        if (pkg) pkg.isActive = true;
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

  hardDelete(id: number) {
  if (confirm("Are you sure you want to permanently delete this package?")) {
    this.packageService.hardDelete(id).subscribe({
      next: () => {
        // remove from list in UI after delete
        this.packages = this.packages.filter(pkg => pkg.packageId !== id);
        alert("Package permanently deleted!");
      },
      error: (err) => {
        console.error("Error deleting package:", err);
        alert("Failed to delete package");
      }
    });
  }
}
}
