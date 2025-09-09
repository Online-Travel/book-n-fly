import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from "../../navbar.component/navbar.component";

@Component({
  selector: 'app-package-navbar',
  imports: [CommonModule, RouterLink, NavbarComponent],
  templateUrl: './package-navbar.html',
  styleUrl: './package-navbar.css'
})
export class PackageNavbar {

}
