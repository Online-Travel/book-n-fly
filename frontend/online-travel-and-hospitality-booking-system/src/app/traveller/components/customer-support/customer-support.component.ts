import { CreateSupportTicket, SupportTicket } from './../../models/supportTicket';
import { Router } from '@angular/router';
import { SupportTicketService } from './../../services/support-ticket.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-customer-support',
  imports: [CommonModule,FormsModule,ReactiveFormsModule],
  templateUrl: './customer-support.component.html',
  styleUrl: './customer-support.component.css'
})
export class CustomerSupportComponent implements OnInit {

  ticketForm: FormGroup;
  tickets: SupportTicket[] = [];
  loading = false;
  selectedTicket: SupportTicket | null = null;
  editMode = false;
  showCreateForm = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private supportTicketService: SupportTicketService
  ) {
    this.ticketForm = this.fb.group({
      issue: ['', [Validators.required, Validators.maxLength(1000)]]
    });
  }

  ngOnInit(): void {
    this.loadMyTickets();
  }

  navigateToDashboard(): void {
    this.router.navigate(['/traveller']);
  }

  loadMyTickets(): void {
    this.loading = true;
    this.supportTicketService.getMyTickets().subscribe({
      next: (tickets) => {
        this.tickets = (tickets || []); 
        this.tickets = tickets.filter(t => typeof t.ticketId === 'number');
        console.log('Loaded tickets:', this.tickets);
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading tickets:', error);
        this.loading = false;
      }
    });
  }

  createTicket(): void {
    if (this.ticketForm.valid) {
      this.loading = true;
      const newTicket: CreateSupportTicket = {
        issue: this.ticketForm.value.issue
      };

      this.supportTicketService.createTicket(newTicket).subscribe({
        next: (ticket) => {
          if (ticket && typeof ticket.ticketId === 'number') {
            this.tickets.unshift(ticket);
          } else {
            console.error('API did not return valid ticket_id', ticket);
          }
          this.ticketForm.reset();
          this.showCreateForm = false;
          this.loading = false;
        },
        error: (error) => {
          console.error('Error creating ticket:', error);
          this.loading = false;
        }
      });
    }
  }

  trackByTicketId(index: number, ticket: SupportTicket): number {
    return ticket.ticketId!;
  }

  updateTicket(ticketId: number, updatedIssue: string): void {
    if (!updatedIssue.trim()) {
      console.error("Issue description cannot be empty");
      return;
    }
    if (typeof ticketId !== 'number' || !ticketId) {
      console.error("Invalid ticket ID for update:", ticketId);
      this.loading = false;
      return;
    }

    this.loading = true;
    const updateData = { issue: updatedIssue };

    this.supportTicketService.updateTicket(ticketId, updateData).subscribe({
      next: (updatedTicket) => {
        if (updatedTicket && typeof updatedTicket.ticketId === 'number') {
          const index = this.tickets.findIndex(t => t.ticketId === ticketId);
          if (index !== -1) {
            this.tickets[index] = updatedTicket;
          }
        } else {
          console.error('API did not return valid ticket_id', updatedTicket);
        }
        this.editMode = false;
        this.selectedTicket = null;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error updating ticket:', error);
        this.loading = false;
      }
    });
  }

  deleteTicket(ticketId: number): void {
    if (typeof ticketId !== 'number' || !ticketId) {
      console.error("Invalid ticket ID for delete:", ticketId);
      return;
    }
    if (confirm('Are you sure you want to delete this ticket?')) {
      this.loading = true;
      this.supportTicketService.deleteTicket(ticketId).subscribe({
        next: () => {
          this.tickets = this.tickets.filter(t => t.ticketId !== ticketId);
          this.loading = false;
        },
        error: (error) => {
          console.error('Error deleting ticket:', error);
          this.loading = false;
        }
      });
    }
  }

  startEdit(ticket: SupportTicket): void {
    if (ticket && typeof ticket.ticketId === 'number') {
      console.log("Editing ticket:", ticket);
      this.selectedTicket = { ...ticket };
      this.editMode = true;
    } else {
      console.error("Cannot edit ticket with invalid ticket_id:", ticket);
    }
  }

  cancelEdit(): void {
    this.editMode = false;
    this.selectedTicket = null;
  }

  getStatusBadgeClass(status: string): string {
    switch (status?.toLowerCase()) {
      case 'open':
        return 'bg-primary';
      case 'in_progress':
        return 'bg-warning text-dark';
      case 'resolved':
        return 'bg-success';
      case 'closed':
        return 'bg-secondary';
      default:
        return 'bg-light text-dark';
    }
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString() + ' ' +
           new Date(dateString).toLocaleTimeString();
  }
}