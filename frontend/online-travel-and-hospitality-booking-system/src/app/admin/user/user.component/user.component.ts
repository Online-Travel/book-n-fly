import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';
import { NavbarComponent } from "../../navbar.component/navbar.component";

declare var bootstrap: any;

@Component({
  selector: 'app-user.component',
  standalone:true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {
  users: User[] = [];
  loading = false;
  errorMessage: string | null = null;

  constructor(private userService: UserService,private cdr: ChangeDetectorRef) {}

  showPopup = false;
  selectedUserId: number | null = null;
  selectedRole: string = '';
  modal:any

  filterUserId: number | null = null;
  currentFilter: 'all' | 'user' = 'all';

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers(): void {
    this.loading = true;
    this.userService.getAllUsers().subscribe({
      next: (data: User[]) => {
        this.users = data;
        this.loading = false;
      },
      error: (err: HttpErrorResponse) => {
        this.errorMessage = err.message;
        this.loading = false;
      }
    });
  }

 openUpdateModal(user: User): void {
    this.selectedUserId = user.userId;
    this.selectedRole = user.role;

    const modalEl = document.getElementById('updateUserModal');
    this.modal = new bootstrap.Modal(modalEl!);
    this.modal.show();
  }

  // Confirm update
  confirmUpdate(): void {
  if (this.selectedUserId == null) return;

  this.userService.updateUser(this.selectedUserId, this.selectedRole).subscribe({
    next: (msg: string) => {
      console.log("Update success:", msg);

      // Refresh users since backend didn’t return updated user
      this.fetchUsers();

      this.modal.hide();
      this.cdr.detectChanges();
    },
    error: (err: HttpErrorResponse) => {
      console.error("Error updating user: " + err.message);
    }
  });
}

 filterUserById(): void {
    if (this.filterUserId == null) return;

    this.loading = true;
    this.userService.getUserById(this.filterUserId).subscribe({
      next: (user: User) => {
        this.users = [user]; // show single result
        this.currentFilter = 'user';
        this.loading = false;
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 404) {
          this.users = [];
          this.errorMessage = 'User not found';
        } else {
          this.errorMessage = err.message;
        }
        this.loading = false;
      }
    });
  }

  // ✅ Reset filter → reload all
  resetFilters(): void {
    this.filterUserId = null;
    this.currentFilter = 'all';
    this.errorMessage = null;
    this.fetchUsers();
  }


  // Call delete user service
  deleteUser(userId: number): void {
    if (!confirm("Are you sure you want to delete this user?")) return;

    this.userService.deleteUser(userId).subscribe({
      next: (msg) => {
        console.log(msg);
        this.users = this.users.filter(u => u.userId !== userId); // update locally
      },
      error: (err: HttpErrorResponse) => {
        console.error("Error deleting user: " + err.message);
      }
    });
  }
}
