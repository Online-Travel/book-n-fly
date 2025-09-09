import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.scss']
})
export class TravelAgentDashboardComponent {
  travelAgent = {
    name: '',
    email: ''
  };

  sidebarOpen = true;

  constructor(private router: Router) {}

  ngOnInit() {
  const email = localStorage.getItem('email') || '';

  this.travelAgent.email = email;

  // Derive name properly from email before avatar renders
  if (email) {
    const namePart = email.split('@')[0];
    const formattedName = namePart
      .split(/[.\-_]/) // split by dot, dash, underscore for multi-part emails
      .map(n => n.charAt(0).toUpperCase() + n.slice(1))
      .join(' ');
    this.travelAgent.name = formattedName;
  } else {
    this.travelAgent.name = 'Agent';
  }
}



  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
  }

  logout() {
    localStorage.removeItem('email');
    this.router.navigate(['/login']);
  }

  getInitials() {
    return this.travelAgent.name ? this.travelAgent.name.charAt(0).toUpperCase() : 'A';
  }
}
