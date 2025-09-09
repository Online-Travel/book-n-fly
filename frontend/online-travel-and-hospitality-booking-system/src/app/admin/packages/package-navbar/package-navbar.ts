import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-package-navbar',
  imports: [CommonModule,RouterLink],
  templateUrl: './package-navbar.html',
  styleUrl: './package-navbar.css'
})
export class PackageNavbar {

}
