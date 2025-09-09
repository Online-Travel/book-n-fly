import { Routes } from '@angular/router';

// Landing & Login
import { LandingPage } from './pages/landing/landing-page/landing-page';
import { LoginComponent } from './pages/auth/login/login.component/login.component';

// Admin Components
import { PaymentComponent } from './admin/payment/payment.component/payment.component';
import { BookingWithPayments } from './admin/payment/booking-with-payments/booking-with-payments';
import { UserWithPayments } from './admin/payment/user-with-payments/user-with-payments';
import { Invoices } from './admin/payment/invoices/invoices';
import { UserComponent } from './admin/user/user.component/user.component';
import { Package } from './admin/packages/package/package';
import { Itinerary } from './admin/packages/itinerary/itinerary';
import { Bookings } from './admin/booking/bookings/bookings';
import { Flights } from './admin/booking/flights/flights';
import { Hotels } from './admin/booking/hotels/hotels';
import { Reviews } from './admin/review/reviews/reviews';
import { TicketComponent } from './admin/review/support-ticket/support-ticket';

// Traveller Components
import { TravellerDashboardComponent } from './traveller/components/traveller-dashboard/traveller-dashboard.component';
import { HotelDetailsComponent } from './traveller/components/hotel-details/hotel-details.component';
import { UserProfileComponent } from './traveller/components/user-profile/user-profile.component';
import { CustomerSupportComponent } from './traveller/components/customer-support/customer-support.component';

// Travel Agent Components
import { TravelAgentDashboardComponent } from './TravelAgent-Dashboard/pages/dashboard/dashboard';
import { PackageComponent } from './TravelAgent-Dashboard/pages/packages/packages';
import { ItinerariesComponent } from './TravelAgent-Dashboard/pages/itineraries/itineraries';
import { SupportTicketComponent } from './TravelAgent-Dashboard/pages/supportticket/supportticket';
import { adminGuard } from './guard/admin/admin-guard';
import { travelerGuard } from './guard/traveler/traveler-guard';
import { agentGuard } from './guard/agent/agent-guard';

export const routes: Routes = [
  { path: '', component: LandingPage },
  { path: 'landing', component: LandingPage },
  { path: 'home', component: LandingPage },
  { path: 'login', component: LoginComponent },

  // Admin routes
  {
    path: 'admin',
    canActivate: [adminGuard],
    children: [
      { path: '', redirectTo: 'user', pathMatch: 'full' },
      { path: 'payment', component: PaymentComponent },
      { path: 'bookingwithpayments', component: BookingWithPayments },
      { path: 'userwithpayments', component: UserWithPayments },
      { path: 'invoices', component: Invoices },
      { path: 'user', component: UserComponent },
      { path: 'package', component: Package },
      { path: 'itinerary', component: Itinerary },
      { path: 'bookings', component: Bookings },
      { path: 'flights', component: Flights },
      { path: 'hotels', component: Hotels },
      { path: 'review', component: Reviews },
      { path: 'ticket', component: TicketComponent },
    ]
  },

  // Traveller routes with children
  {
    path: 'traveller',
    component: TravellerDashboardComponent,
    canActivate: [travelerGuard],
    children: [
      { path: '', redirectTo: 'profile', pathMatch: 'full' },
      { path: 'profile', component: UserProfileComponent },
      { path: 'hotel/:id', component: HotelDetailsComponent },
      { path: 'customer-support', component: CustomerSupportComponent },
    ]
  },

  // Travel Agent
  {
    path: 'agent',
    component: TravelAgentDashboardComponent,
    canActivate: [agentGuard],
    children: [
      { path: '', redirectTo: 'packages', pathMatch: 'full' },
      { path: 'packages', component: PackageComponent },
      { path: 'itineraries', component: ItinerariesComponent },
      { path: 'supportticket', component: SupportTicketComponent }
    ]
  },

  // Wildcard fallback
  { path: '**', redirectTo: 'login' }
];

