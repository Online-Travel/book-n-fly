import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ReviewNavbar } from '../review-navbar/review-navbar';
import { AuthService } from '../../../auth/auth.service';
import { FormsModule } from '@angular/forms';
import { SupportTicket } from '../../models/review.model';
import { ReviewService } from '../../services/review.service';

@Component({
  selector: 'app-support-ticket',
  standalone:true,
  imports: [CommonModule,ReviewNavbar,FormsModule],
  templateUrl: './support-ticket.html',
  styleUrl: './support-ticket.css'
})
export class TicketComponent{
  ticketId: number | null = null;
  agentId: number | null = null;

  tickets: SupportTicket[] = [];
  selected?: SupportTicket;
  error: string | null = null;
  loading = false;

  constructor(private ticketSvc: ReviewService, public auth: AuthService) {}

  loadAllForAdmin(): void {
    this.loading = true; this.error = null;
    this.ticketSvc.getAllTickets().subscribe({
      next: (res) => { this.tickets = res; this.loading = false; },
      error: () => { this.error = 'Failed to load all tickets (admin only).'; this.loading = false; }
    });
  }

  loadOpenForAdmin(): void {
    this.loading = true; this.error = null;
    this.ticketSvc.getOpenTickets().subscribe({
      next: (res) => { this.tickets = res; this.loading = false; },
      error: () => { this.error = 'Failed to load open tickets (admin only).'; this.loading = false; }
    });
  }

  searchById(): void {
    if (this.ticketId == null) return;
    this.loading = true; this.error = null; this.selected = undefined;
    this.ticketSvc.getTicketById(this.ticketId).subscribe({
      next: (res) => { this.selected = res; this.loading = false; },
      error: () => { this.error = 'Ticket not found or access denied.'; this.loading = false; }
    });
  }

  assign(): void {
    if (this.ticketId == null || this.agentId == null) return;
    this.loading = true; this.error = null;
    this.ticketSvc.assignTicket(this.ticketId, this.agentId).subscribe({
      next: (res) => { this.selected = res; this.loading = false; },
      error: () => { this.error = 'Assign failed (admin only).'; this.loading = false; }
    });
  }

  reset(): void {
    this.ticketId = null;
    this.agentId = null;
    this.selected = undefined;
    this.tickets = [];
    this.error = null;
  }

}
