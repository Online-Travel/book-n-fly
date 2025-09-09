import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private API_URL = 'http://localhost:8080/payments';
  private INVOICE_URL = 'http://localhost:8080/invoice';
  private Booking_URL = 'http://localhost:8080/api/bookings';


  constructor(private http: HttpClient) { }

  // Create Razorpay order
  createOrder(amount: number): Observable<any> {
    return this.http.post(`${this.API_URL}/create-order?amount=${amount}`, {});
  }

  addBooking(bookingData: any): Observable<any> {
    const token = localStorage.getItem('authToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.post(`${this.Booking_URL}`, bookingData, { headers });
  }

  // Save payment after successful Razorpay payment
  savePayment(paymentData: any): Observable<any> {
    const token = localStorage.getItem('authToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    
    return this.http.post(`${this.API_URL}/create`, paymentData, { headers });
  }

  // Send invoice email - This is in InvoiceController
  sendInvoiceEmail(invoiceId: number): Observable<any> {
    const token = localStorage.getItem('authToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    
    return this.http.post(`${this.INVOICE_URL}/mail/${invoiceId}`, {}, { headers, responseType: 'text' });
  }

  // Get user's payments
  getUserPayments(): Observable<any> {
    const token = localStorage.getItem('authToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    
    return this.http.get(`${this.API_URL}/user`, { headers });
  }

  // Get payment by ID
  getPaymentById(paymentId: number): Observable<any> {
    const token = localStorage.getItem('authToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    
    return this.http.get(`${this.API_URL}/${paymentId}`, { headers });
  }
}