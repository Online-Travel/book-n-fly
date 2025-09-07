import { Routes } from '@angular/router';
import { TravellerDashboardComponent } from './components/traveller-dashboard/traveller-dashboard.component';
import { LoginComponent } from './components/login/login';
import { Landing } from './components/landing/landing';
import { HotelDetailsComponent } from './components/hotel-details/hotel-details.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { CustomerSupportComponent } from './components/customer-support/customer-support.component';

export const routes: Routes = [
    { path: '', component: Landing, pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'traveller-dashboard', component: TravellerDashboardComponent },
  {path: 'hotel/:id', component: HotelDetailsComponent},
  { path: 'userProfile', component: UserProfileComponent }, 
  { path: 'customer-support', component: CustomerSupportComponent },
  { path: '**', redirectTo: 'login' }
];
