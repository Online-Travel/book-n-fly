import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PackageService } from '../../services/package';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.html'
})
export class DashboardComponent implements OnInit {
  packages: any[] = [];
  packageForm: any = {};
  activitiesInput: string = "";

  constructor(
    private packageService: PackageService,
    public auth: AuthService // <--- fix for logout button
  ) {}

  ngOnInit(): void {
    this.loadPackages();
  }

  loadPackages() {
    this.packageService.getPackages().subscribe((data: any) => {
      this.packages = data;
    });
  }

  onSubmit() {
    this.packageForm.activities = this.activitiesInput
      ? this.activitiesInput.split(',').map(a => a.trim())
      : [];

    if (this.packageForm.id) {
      this.packageService.updatePackage(this.packageForm.id, this.packageForm)
        .subscribe(() => {
          this.loadPackages();
          this.resetForm();
        });
    } else {
      this.packageService.addPackage(this.packageForm)
        .subscribe(() => {
          this.loadPackages();
          this.resetForm();
        });
    }
  }

  editPackage(pkg: any) {
    this.packageForm = { ...pkg };
    this.activitiesInput = pkg.activities ? pkg.activities.join(", ") : "";
  }

  softDelete(id: number) {
    this.packageService.softDeletePackage(id).subscribe(() => {
      this.loadPackages();
    });
  }

  hardDelete(id: number) {
    if (confirm("Are you sure you want to permanently delete this package?")) {
      this.packageService.hardDeletePackage(id).subscribe(() => {
        this.loadPackages();
      });
    }
  }

  resetForm() {
    this.packageForm = {};
    this.activitiesInput = "";
  }
}
