import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component/login.component';
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
import { LandingPage } from './pages/landing/landing-page/landing-page';

export const routes: Routes = [
    { path: '', component: LandingPage},
    { path: 'landing', component: LandingPage},
    { path: 'home', component: LandingPage},
    //  { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
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
    {path:'ticket',component:TicketComponent}
];
