import { Component } from '@angular/core';
import {  RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from "../../navbar.component/navbar.component";

@Component({
  selector: 'app-review-navbar',
  imports: [CommonModule, RouterModule, NavbarComponent],
  templateUrl: './review-navbar.html',
  styleUrl: './review-navbar.css'
})
export class ReviewNavbar {

}
