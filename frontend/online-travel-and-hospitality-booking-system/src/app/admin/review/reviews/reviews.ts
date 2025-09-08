import { Component } from '@angular/core';
import { ReviewNavbar } from '../review-navbar/review-navbar';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReviewService } from '../../services/review.service';
import { Review } from '../../models/review.model';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-reviews',
  standalone: true,
  imports: [ReviewNavbar,CommonModule,FormsModule],
  templateUrl: './reviews.html',
  styleUrl: './reviews.css'
})
export class Reviews {
  hotelId: number | null = null;
  reviews: Review[] = [];
  error: string | null = null;
  loading = false;

  constructor(private reviewSvc: ReviewService, public auth: AuthService) {}

  loadAllForAdmin(): void {
    this.loading = true;
    this.error = null;
    this.reviewSvc.getAllReviews().subscribe({
      next: (res) => { this.reviews = res; this.loading = false; },
      error: () => { this.error = 'Failed to load all reviews (admin only).'; this.loading = false; }
    });
  }

  searchByHotel(): void {
    if (this.hotelId == null) return;
    this.loading = true;
    this.error = null;
    this.reviewSvc.getReviewsByHotel(this.hotelId).subscribe({
      next: (res) => { this.reviews = res; this.loading = false; },
      error: () => { this.reviews = []; this.error = 'No reviews found for this hotel or access denied.'; this.loading = false; }
    });
  }

  reset(): void {
    this.hotelId = null;
    this.reviews = [];
    this.error = null;
  }

}
