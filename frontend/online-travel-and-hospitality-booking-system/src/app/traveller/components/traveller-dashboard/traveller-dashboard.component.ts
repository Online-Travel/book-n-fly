import { Component, OnInit } from '@angular/core';
import { BookingService } from '../../services/booking.service';
import { Hotel } from '../../models/hotel';
import { Flight } from '../../models/flight';
import { Package } from '../../models/package';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { PaymentService } from '../../services/payment.service';
declare var Razorpay: any;

@Component({
  selector: 'app-traveller-dashboard',
  imports: [FormsModule,CommonModule],
  templateUrl: './traveller-dashboard.component.html',
  styleUrl: './traveller-dashboard.component.css'
})
export class TravellerDashboardComponent implements OnInit {
  activeTab: string = 'hotels';
  loading: boolean = false;
  // Data arrays
  hotels: Hotel[] = [];
  flights: Flight[] = [];
  packages: Package[] = [];
  // Displayed data
  filteredData: any[] = [];
  // Filters
  searchText: string = '';
  minPrice: number = 0;
  maxPrice: number = 50000;
  minRating: number = 0;
  maxRating: number = 5;
  minRooms: number = 0;
  maxRooms: number = 100;
  selectedLocation: string = '';
  selectedAirline: string = '';
  selectedDeparture: string = '';
  selectedArrival: string = '';
  availabilityFilter: string = 'all';
  // Unique dropdown values
  locations: string[] = [];
  airlines: string[] = [];
  departures: string[] = [];
  arrivals: string[] = [];
  destinations: string[] = [];
  // Sorting
  sortBy: string = '';
  sortOrder: string = 'asc';

  // Itinerary Form Properties
  showItineraryForm: boolean = false;
  selectedPackage: Package | null = null;
  submittingItinerary: boolean = false;
  // Payment for flights
  paymentProcessing: boolean = false;
  
  itineraryForm = {
    packageId: 0,
    customizationDetails: '',
    startDate: '',
    endDate: '',
    numberOfTravelers: 1
  };

  constructor(private bookingService: BookingService, private router: Router, private paymentService: PaymentService) { }

  ngOnInit(): void {
    this.loadData();
  }

  setActiveTab(tab: string): void {
    this.activeTab = tab;
    this.resetFilters();
    this.loadData();
  }

  loadData(): void {
    this.loading = true;
    switch (this.activeTab) {
      case 'hotels':
        this.bookingService.getAllHotels().subscribe({
          next: (data) => {
            this.hotels = data;
            this.filteredData = [...data];
            this.extractUniqueLocations();
            this.loading = false;
          },
          error: () => { this.loading = false; }
        });
        break;
      case 'flights':
        this.bookingService.getAllFlights().subscribe({
          next: (data) => {
            this.flights = data;
            this.filteredData = [...data];
            this.extractUniqueFlightData();
            this.loading = false;
          },
          error: () => { this.loading = false; }
        });
        break;
      case 'packages':
        this.bookingService.getActivePackages().subscribe({
          next: (data) => {
            this.packages = data;
            this.filteredData = [...data];
            this.extractUniqueDestinations();
            this.loading = false;
          },
          error: () => { this.loading = false; }
        });
        break;
    }
  }

  extractUniqueLocations(): void {
    this.locations = [...new Set(this.hotels.map(h => h.location))].sort();
  }
  extractUniqueFlightData(): void {
    this.airlines = [...new Set(this.flights.map(f => f.airline))].sort();
    this.departures = [...new Set(this.flights.map(f => f.departure))].sort();
    this.arrivals = [...new Set(this.flights.map(f => f.arrival))].sort();
  }
  extractUniqueDestinations(): void {
    this.destinations = [...new Set(this.packages.map(p => p.destination))].sort();
  }

  /** NEW: Use BookingService APIs for search/filter */
  applyFiltersAndSearch(): void {
    this.loading = true;
    if (this.activeTab === 'hotels') {
      this.bookingService.searchHotels(
        this.selectedLocation || undefined,
        this.minRating || undefined,
        this.maxRating || undefined,
        this.minPrice || undefined,
        this.maxPrice || undefined,
        this.minRooms || undefined,
        this.maxRooms || undefined
      ).subscribe({
        next: (data) => {
          this.filteredData = data;
          this.extractUniqueLocations();
          this.applySearchText();
          this.applySort();
          this.loading = false;
        },
        error: () => { this.loading = false; }
      });
    } else if (this.activeTab === 'flights') {
      // Availability: string filter to boolean
      let avail: boolean | undefined = undefined;
      if (this.availabilityFilter === 'available') avail = true;
      else if (this.availabilityFilter === 'unavailable') avail = false;
      this.bookingService.searchFlights(
        this.selectedAirline || undefined,
        this.selectedDeparture || undefined,
        this.selectedArrival || undefined,
        this.minPrice || undefined,
        this.maxPrice || undefined,
        avail
      ).subscribe({
        next: (data) => {
          this.filteredData = data;
          this.extractUniqueFlightData();
          this.applySearchText();
          this.applySort();
          this.loading = false;
        },
        error: () => { this.loading = false; }
      });
    } else if (this.activeTab === 'packages') {
      this.loading = true;

  const hasDestination = !!this.selectedLocation?.trim();
  const hasPrice = this.minPrice != null || this.maxPrice != null;

  // both filters given → fetch destination first, then filter by price locally
  if (hasDestination && hasPrice) {
    this.bookingService.searchPackagesByDestination(this.selectedLocation!.trim())
      .subscribe({
        next: (data) => {
          this.filteredData = data.filter(pkg =>
            pkg.price >= (this.minPrice ?? 0) &&
            pkg.price <= (this.maxPrice ?? 1000000)
          );
          this.extractUniqueDestinations();
          this.applySearchText();
          this.applySort();
          this.loading = false;
        },
        error: () => { this.loading = false; }
      });

  // only destination
  } else if (hasDestination) {
    this.bookingService.searchPackagesByDestination(this.selectedLocation!.trim())
      .subscribe({
        next: (data) => {
          this.filteredData = data;
          this.extractUniqueDestinations();
          this.applySearchText();
          this.applySort();
          this.loading = false;
        },
        error: () => { this.loading = false; }
      });

  // only price
  } else if (hasPrice) {
    this.bookingService.searchPackagesByPrice(this.minPrice, this.maxPrice)
      .subscribe({
        next: (data) => {
          this.filteredData = data;
          this.extractUniqueDestinations();
          this.applySearchText();
          this.applySort();
          this.loading = false;
        },
        error: () => { this.loading = false; }
      });

  // nothing given → fetch all
  } else {
    this.bookingService.getAllPackages()
      .subscribe({
        next: (data) => {
          this.filteredData = data;
          this.extractUniqueDestinations();
  this.applySort();
  this.loading = false;
        },
        error: () => { this.loading = false; }
      });
  }
    }
  }

  // Sorting: still triggers instantly
  onSortChange(): void {
    this.applySort();
  }
  toggleSortOrder(): void {
    this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
    this.applySort();
  }
  applySort(): void {
    if (!this.sortBy) return;
    this.filteredData.sort((a, b) => {
      let aValue = a[this.sortBy];
      let bValue = b[this.sortBy];
      if (typeof aValue === 'string') {
        aValue = aValue.toLowerCase();
        bValue = bValue.toLowerCase();
      }
      if (this.sortOrder === 'asc') return aValue > bValue ? 1 : -1;
      else return aValue < bValue ? 1 : -1;
    });
  }

  resetFilters(): void {
    this.searchText = '';
    this.sortBy = '';
    this.sortOrder = 'asc';
    this.minPrice = 0;
    this.maxPrice = 50000;
    this.minRating = 0;
    this.maxRating = 5;
    this.minRooms = 0;
    this.maxRooms = 100;
    this.selectedLocation = '';
    this.selectedAirline = '';
    this.selectedDeparture = '';
    this.selectedArrival = '';
    this.availabilityFilter = 'all';
  }
  
  getImageUrl(item: any): string {
    switch (this.activeTab) {
      case 'hotels': return`hotel-1.jpg`;
      case 'flights': return `flight-1.png`;
      case 'packages': return `hotel-3.jpg`;
      default: return `hotel-4.jpg`;
    }
    
  }
  getRatingStars(rating: number): string[] {
    const fullStars = Math.floor(rating);
    const hasHalfStar = rating % 1 >= 0.5;
    const stars: string[] = [];
    for (let i = 0; i < fullStars; i++) stars.push('fas fa-star');
    if (hasHalfStar) stars.push('fas fa-star-half-alt');
    const emptyStars = 5 - Math.ceil(rating);
    for (let i = 0; i < emptyStars; i++) stars.push('far fa-star');
    return stars;
  }

  private applySearchText(): void {
    if (!this.searchText.trim()) return;

    const lowerSearch = this.searchText.toLowerCase();
    this.filteredData = this.filteredData.filter(item => {
      // Dynamically check common properties across hotels, flights, and packages
      return Object.values(item).some(val =>
        val && val.toString().toLowerCase().includes(lowerSearch)
      );
    });
  }

  

  // Itinerary Form Methods
  closeItineraryForm(): void {
    this.showItineraryForm = false;
    this.selectedPackage = null;
    this.resetItineraryForm();
  }

  resetItineraryForm(): void {
    this.itineraryForm = {
      packageId: 0,
      customizationDetails: '',
      startDate: '',
      endDate: '',
      numberOfTravelers: 1
    };
  }

  submitItinerary(): void {
    if (!this.validateItineraryForm()) {
      return;
    }

    this.submittingItinerary = true;

    // Format dates to include time as shown in your example
    const formattedData = {
      ...this.itineraryForm,
      startDate: new Date(this.itineraryForm.startDate).toISOString(),
      endDate: new Date(this.itineraryForm.endDate).toISOString()
    };

    this.bookingService.createItinerary(formattedData)
      .subscribe({
        next: (response) => {
          console.log('Itinerary created successfully:', response);
          alert('Itinerary created successfully! Your package booking is confirmed.');
          this.closeItineraryForm();
          this.submittingItinerary = false;
        },
        error: (error) => {
          console.error('Error creating itinerary:', error);
          alert('Failed to create itinerary. Please try again.');
          this.submittingItinerary = false;
        }
      });
  }

  private validateItineraryForm(): boolean {
    if (!this.itineraryForm.startDate) {
      alert('Please select a start date');
      return false;
    }
    if (!this.itineraryForm.endDate) {
      alert('Please select an end date');
      return false;
    }
    if (new Date(this.itineraryForm.startDate) >= new Date(this.itineraryForm.endDate)) {
      alert('End date must be after start date');
      return false;
    }
    if (this.itineraryForm.numberOfTravelers < 1) {
      alert('Number of travelers must be at least 1');
      return false;
    }
    if (!this.itineraryForm.customizationDetails.trim()) {
      alert('Please provide customization details');
      return false;
    }
    return true;
  }

  // Helper method to get minimum date (today)
  getMinDate(): string {
    const today = new Date();
    return today.toISOString().split('T')[0];
  }

  // Helper method to get minimum end date (start date + 1 day)
  getMinEndDate(): string {
    if (!this.itineraryForm.startDate) return this.getMinDate();
    const startDate = new Date(this.itineraryForm.startDate);
    startDate.setDate(startDate.getDate() + 1);
    return startDate.toISOString().split('T')[0];
  }

  // Navigation methods for user profile dropdown
navigateToProfile(event: Event): void {
  event.preventDefault();
  this.router.navigate(['/userProfile']);
}

navigateToSupport(event: Event): void {
  event.preventDefault();
  this.router.navigate(['/customer-support']);
}


bookItem(item: any): void {
    if (this.activeTab === 'hotels') {
      this.router.navigate(['/hotel', item.hotelId]);
    } else if (this.activeTab === 'packages') {
      // Show itinerary form for packages
      this.selectedPackage = item;
      this.itineraryForm.packageId = item.packageId || item.id;
      this.showItineraryForm = true;
    } else {
      if(this.activeTab==='flights'){
          const confirmed = window.confirm('Do you want to book this flight and proceed to payment?');
        if (confirmed) {
          this.initiatePayment(item);
        } else {
          console.log('Flight booking cancelled by user.');
        }
      }
    }
  }
  initiatePayment(flight:Flight): void {
    const amount=flight.price;
    
    this.paymentProcessing = true;
    const amountInPaise = amount * 100; // Convert to paise for Razorpay

    this.paymentService.createOrder(amountInPaise).subscribe({
      next: (order) => {
        this.openRazorpayCheckout(order, amount, flight);
      },
      error: (error) => {
        console.error('Error creating order:', error);
        alert('Failed to create payment order. Please try again.');
        this.paymentProcessing = false;
      }
    });
  }

  openRazorpayCheckout(order: any, originalAmount: number,flight:Flight): void {
    const options = {
      key: 'rzp_test_m17StH2RvJgYRu', // Replace with your Razorpay Key ID
      amount: order.amount,
      currency: order.currency,
      name: 'Travel Booking',
      description: `Flight Booking - ${flight.airline} from ${flight.departure} to ${flight.arrival}`,
      order_id: order.id,
      handler: (response: any) => {
        this.handlePaymentSuccess(response, originalAmount, flight);
      },
      prefill: {
        name: 'Traveler',
        email: 'traveler@example.com',
        contact: '9999999999'
      },
      theme: {
        color: '#0d6efd'
      },
      modal: {
        ondismiss: () => {
          this.paymentProcessing = false;
          console.log('Payment dialog closed');
        }
      }
    };

    const rzp = new Razorpay(options);
    rzp.open();
  }

  handlePaymentSuccess(response: any, amount: number,flight:Flight): void {
    const bookingData={
      type: 'FLIGHT',
      itemId: flight?.flightId ,
    }
    // Create payment data matching your backend PaymentRequestDTO
    const paymentData = {
      bookingId: 0, // You need to either create a booking first or get this from somewhere
      amount: amount,
      paymentMethod: 'RAZORPAY'
    };

    console.log('Sending payment data:', paymentData);
    console.log('Razorpay response:', response);

    this.paymentService.addBooking(bookingData).subscribe({
  next: (bookingResponse) => {
    console.log('Booking created successfully:', bookingResponse);
    
    paymentData.bookingId = bookingResponse.bookingId;  // assign booking ID here

    // Now save the payment with correct bookingId
    this.paymentService.savePayment(paymentData).subscribe({
      next: (paymentResponse) => {
        console.log('Payment saved successfully:', paymentResponse);
        alert(`Payment successful! Booking confirmed for ${flight?.airline}.
               Payment ID: ${response.razorpay_payment_id}
               Booking ID: ${paymentResponse.bookingId || 'N/A'}
               Amount: ₹${paymentResponse.amount}`);
        this.paymentProcessing = false;
      },
      error: (error) => {
        console.error('Error saving payment:', error);
        alert('Payment was successful but there was an issue saving the booking. Please contact support.');
        this.paymentProcessing = false;
      }
    });
  },
  error: (error) => {
    console.error('Error creating booking:', error);
    alert('Booking failed. Please try again.');
    this.paymentProcessing = false;
  }
});

  }

logout(event: Event): void {
  event.preventDefault();
  
  // Clear authentication data
  localStorage.removeItem('authToken');
  localStorage.removeItem('userRole');
  localStorage.removeItem('userId');
  // Clear any other stored user data
  
  // Show logout message (optional)
  alert('You have been logged out successfully.');
  
  // Navigate to login page
  this.router.navigate(['/login']);

}


}
