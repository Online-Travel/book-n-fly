import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookingService } from '../../services/booking.service';
import { Hotel } from '../../models/hotel';
import { Review } from '../../models/review';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PaymentService } from '../../services/payment.service';


declare var Razorpay: any;

@Component({
  selector: 'app-hotel-details',
  imports: [CommonModule, FormsModule],
  templateUrl: './hotel-details.component.html',
  styleUrl: './hotel-details.component.css'
})
export class HotelDetailsComponent implements OnInit {
  hotel: Hotel | null = null;

  reviews: Review[] = [];
  loading: boolean = false;
  reviewsLoading: boolean = false;
  hotelId: number = 0;
  paymentProcessing: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookingService: BookingService,
    private paymentService: PaymentService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.hotelId = +params['id'];
      if (this.hotelId) {
        this.loadHotelDetails();
        this.loadHotelReviews();
      }
    });
  }

  loadHotelDetails(): void {
    this.loading = true;
    // Since there's no specific getHotelById API, we'll get all hotels and filter
    this.bookingService.getAllHotels().subscribe({
      next: (hotels) => {
        this.hotel = hotels.find(h => h.hotelId === this.hotelId) || null;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        console.error('Error loading hotel details');
      }
    });
  }

  loadHotelReviews(): void {
    this.reviewsLoading = true;
    this.bookingService.getHotelReviews(this.hotelId).subscribe({
      next: (reviews) => {
        this.reviews = reviews;
        this.reviewsLoading = false;
      },
      error: () => {
        this.reviewsLoading = false;
        console.error('Error loading hotel reviews');
      }
    });
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

  getReviewRatingStars(rating: number): string[] {
    return this.getRatingStars(rating);
  }

  bookHotel(): void {
    if (this.hotel) {
      this.initiatePayment(this.hotel.pricePerNight);
    }
  }

  initiatePayment(amount: number): void {
    if (!this.hotel) return;
    
    this.paymentProcessing = true;
    const amountInPaise = amount * 100; // Convert to paise for Razorpay

    this.paymentService.createOrder(amountInPaise).subscribe({
      next: (order) => {
        this.openRazorpayCheckout(order, amount);
      },
      error: (error) => {
        console.error('Error creating order:', error);
        alert('Failed to create payment order. Please try again.');
        this.paymentProcessing = false;
      }
    });
  }

  openRazorpayCheckout(order: any, originalAmount: number): void {
    const options = {
      key: 'rzp_test_m17StH2RvJgYRu', // Replace with your Razorpay Key ID
      amount: order.amount,
      currency: order.currency,
      name: 'Travel Booking',
      description: `Hotel Booking - ${this.hotel?.name}`,
      order_id: order.id,
      handler: (response: any) => {
        this.handlePaymentSuccess(response, originalAmount);
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

  handlePaymentSuccess(response: any, amount: number): void {
    const bookingData={
      type: 'HOTEL',
      itemId: this.hotel?.hotelId ,
    }
    // Create payment data matching your backend PaymentRequestDTO
    const paymentData = {
      bookingId: 11, // You need to either create a booking first or get this from somewhere
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
        alert(`Payment successful! Booking confirmed for ${this.hotel?.name}
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

  goBack(): void {
    this.router.navigate(['/traveller-dashboard']);
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-IN', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getAverageRating(): number {
    if (this.reviews.length === 0) return 0;
    const sum = this.reviews.reduce((acc, review) => acc + review.rating, 0);
    return Math.round((sum / this.reviews.length) * 10) / 10;
  }
}