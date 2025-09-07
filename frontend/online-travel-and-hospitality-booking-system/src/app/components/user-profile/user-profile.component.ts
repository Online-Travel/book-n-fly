import { UserService ,Booking,Itinerary,ItineraryUpdate,Review} from './../../services/user.service';
import { Component } from '@angular/core';
import { AuthService } from '../../services/auth';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  imports: [CommonModule,ReactiveFormsModule,FormsModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent {
userEmail: string | null = null;
  activeTab: string = 'profile';

  // Bookings
  bookings: Booking[] = [];
  displayedBookings: Booking[] = [];
  showAllBookings: boolean = false;
  showReviewModal: boolean = false;
  selectedBooking: Booking | null = null;
  reviewForm: Review = {
    hotelId: 0,
    rating: 5,
    comment: ''
  };

  // Itineraries
  itineraries: Itinerary[] = [];
  showItineraryModal: boolean = false;
  editingItinerary: Itinerary | null = null;
  itineraryForm: ItineraryUpdate = {
    packageId: 0,
    customizationDetails: '',
    startDate: '',
    endDate: '',
    numberOfTravelers: 1
  };

  // Loading states
  loadingBookings: boolean = false;
  loadingItineraries: boolean = false;
  submittingReview: boolean = false;
  savingItinerary: boolean = false;

  constructor(
    private authService: AuthService,
    private userService: UserService,
  private router: Router
  ) {}

  ngOnInit() {
    this.userEmail = this.authService.getUserEmail();
    this.loadBookings();
    this.loadItineraries();
  }

  // Tab management
  setActiveTab(tab: string) {
    this.activeTab = tab;
  }

  // Bookings methods
  loadBookings() {
    this.loadingBookings = true;
    this.userService.getMyBookings().subscribe({
      next: (bookings) => {
        this.bookings = bookings;
        this.updateDisplayedBookings();
        this.loadingBookings = false;
      },
      error: (error) => {
        console.error('Error loading bookings:', error);
        this.loadingBookings = false;
      }
    });
  }

  updateDisplayedBookings() {
    this.displayedBookings = this.showAllBookings ? 
      this.bookings : this.bookings.slice(0, 5);
  }

  toggleShowAllBookings() {
    this.showAllBookings = !this.showAllBookings;
    this.updateDisplayedBookings();
  }

  openReviewModal(booking: Booking) {
    this.selectedBooking = booking;
    this.reviewForm = {
      hotelId: booking.itemId,
      rating: 5,
      comment: ''
    };
    this.showReviewModal = true;
  }

  closeReviewModal() {
    this.showReviewModal = false;
    this.selectedBooking = null;
  }

  submitReview() {
    if (!this.reviewForm.comment.trim()) {
      alert('Please enter a comment for your review.');
      return;
    }

    this.submittingReview = true;
    this.userService.submitReview(this.reviewForm).subscribe({
      next: (response) => {
        alert('Review submitted successfully!');
        this.closeReviewModal();
        this.submittingReview = false;
      },
      error: (error) => {
        console.error('Error submitting review:', error);
        alert('Error submitting review. Please try again.');
        this.submittingReview = false;
      }
    });
  }

  // Itineraries methods
  loadItineraries() {
    this.loadingItineraries = true;
    this.userService.getMyItineraries().subscribe({
      next: (itineraries) => {
        this.itineraries = itineraries;
        this.loadingItineraries = false;
      },
      error: (error) => {
        console.error('Error loading itineraries:', error);
        this.loadingItineraries = false;
      }
    });
  }

  openItineraryModal(itinerary?: Itinerary) {
    if (itinerary) {
      this.editingItinerary = itinerary;
      this.itineraryForm = {
        packageId: itinerary.packageId,
        customizationDetails: itinerary.customizationDetails,
        startDate: itinerary.startDate.split('T')[0],
        endDate: itinerary.endDate.split('T')[0],
        numberOfTravelers: itinerary.numberOfTravelers
      };
    } else {
      this.editingItinerary = null;
      this.itineraryForm = {
        packageId: 0,
        customizationDetails: '',
        startDate: '',
        endDate: '',
        numberOfTravelers: 1
      };
    }
    this.showItineraryModal = true;
  }

  closeItineraryModal() {
    this.showItineraryModal = false;
    this.editingItinerary = null;
  }

  saveItinerary() {
    if (!this.itineraryForm.customizationDetails.trim() || 
        !this.itineraryForm.startDate || 
        !this.itineraryForm.endDate) {
      alert('Please fill in all required fields.');
      return;
    }

    this.savingItinerary = true;
    const payload = {
    ...this.itineraryForm,
    startDate: this.formatToDateTime(this.itineraryForm.startDate),
    endDate: this.formatToDateTime(this.itineraryForm.endDate)
  };

    if (this.editingItinerary) {
      this.userService.updateItinerary(this.editingItinerary.itineraryId, payload).subscribe({
        next: (response) => {
          alert('Itinerary updated successfully!');
          this.loadItineraries();
          this.closeItineraryModal();
          this.savingItinerary = false;
        },
        error: (error) => {
          console.error('Error updating itinerary:', error);
          alert('Error updating itinerary. Please try again.');
          this.savingItinerary = false;
        }
      });
    }
  }

  deleteItinerary(itinerary: Itinerary) {
    if (confirm('Are you sure you want to delete this itinerary?')) {
      this.userService.deleteItinerary(itinerary.itineraryId).subscribe({
        next: (response) => {
          alert('Itinerary deleted successfully!');
          this.loadItineraries();
        },
        error: (error) => {
          console.error('Error deleting itinerary:', error);
          alert('Error deleting itinerary. Please try again.');
        }
      });
    }
  }

  // Utility methods
  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString();
  }
  private formatToDateTime(date: string): string {
  // If user selected only YYYY-MM-DD, add T00:00:00
  return date.includes('T') ? date : `${date}T00:00:00`;
}

  getStatusBadgeClass(status: string): string {
    switch (status.toLowerCase()) {
      case 'success': return 'bg-success';
      case 'pending': return 'bg-warning text-dark';
      case 'draft': return 'bg-secondary';
      default: return 'bg-secondary';
    }
  }
  navigateBack(): void {

  this.router.navigate(['traveller-dashboard']);
}

  onLogout() {
    this.authService.logout();
  }
}