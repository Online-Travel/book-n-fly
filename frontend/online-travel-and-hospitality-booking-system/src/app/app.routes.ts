import { Routes } from '@angular/router';
import { LandingPage } from './pages/landing/landing-page/landing-page';
import { LoginComponent } from './pages/auth/login/login.component/login.component';
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
import { TravellerDashboardComponent } from './traveller/components/traveller-dashboard/traveller-dashboard.component';
import { HotelDetailsComponent } from './traveller/components/hotel-details/hotel-details.component';
import { UserProfileComponent } from './traveller/components/user-profile/user-profile.component';
import { CustomerSupportComponent } from './traveller/components/customer-support/customer-support.component';

export const routes: Routes = [
    { path: '', component: LandingPage},
    { path: 'landing', component: LandingPage},
    { path: 'home', component: LandingPage},
    //  { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    // Admin
    {path:'payment',component:PaymentComponent},
    {path:'bookingwithpayments',component:BookingWithPayments},
    {path:'userwithpayments',component:UserWithPayments},
    {path:'invoices',component:Invoices},
    {path:'user',component:UserComponent},
    {path:'package',component:Package},
    {path:'itinerary',component:Itinerary},
    {path:'bookings',component:Bookings},
    {path:'flights',component:Flights},
    {path:'hotels',component:Hotels},
    {path:'review',component:Reviews},
    {path:'ticket',component:TicketComponent},
    // Traveler
    { path: 'traveller-dashboard', component: TravellerDashboardComponent },
  {path: 'hotel/:id', component: HotelDetailsComponent},
  { path: 'userProfile', component: UserProfileComponent }, 
  { path: 'customer-support', component: CustomerSupportComponent },
  { path: '**', redirectTo: 'login' }
];
