import { Routes } from '@angular/router';
//import { LoginComponent } from './pages/login/login';
import { TravelAgentDashboardComponent } from './TravelAgent-Dashboard/pages/dashboard/dashboard';
import { PackageComponent } from './TravelAgent-Dashboard/pages/packages/packages';
//import { Landing } from './pages/landing/landing';
import { ItinerariesComponent } from './TravelAgent-Dashboard/pages/itineraries/itineraries';
import { SupportTicketComponent } from './TravelAgent-Dashboard/pages/supportticket/supportticket';

export const routes: Routes = [
  { 
    path: '', 
    redirectTo: 'dashboard', 
    pathMatch: 'full' 
  },
  {
    path: 'dashboard',
    component: TravelAgentDashboardComponent,
    children: [
      { path: '', redirectTo: 'packages', pathMatch: 'full' }, // default
      { path: 'packages', component: PackageComponent },
      { path: 'itineraries', component: ItinerariesComponent },
      { path: 'supportticket', component: SupportTicketComponent }
    ]
  },
  { path: '**', redirectTo: 'dashboard' }
];

