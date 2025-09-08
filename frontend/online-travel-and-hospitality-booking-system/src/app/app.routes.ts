import { Routes } from '@angular/router';
import { Landing } from './traveller/components/landing/landing';
import { LoginComponent } from './traveller/components/login/login';
import { TravellerDashboardComponent } from './traveller/components/traveller-dashboard/traveller-dashboard.component';
import { HotelDetailsComponent } from './traveller/components/hotel-details/hotel-details.component';
import { UserProfileComponent } from './traveller/components/user-profile/user-profile.component';
import { CustomerSupportComponent } from './traveller/components/customer-support/customer-support.component';


export const routes: Routes = [
    { path: '', component: Landing, pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'traveller-dashboard', component: TravellerDashboardComponent },
  {path: 'hotel/:id', component: HotelDetailsComponent},
  { path: 'userProfile', component: UserProfileComponent }, 
  { path: 'customer-support', component: CustomerSupportComponent },
  { path: '**', redirectTo: 'login' }
];
