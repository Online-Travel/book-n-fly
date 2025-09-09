import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PaymentResponseDTO, UserPaymentResponseDTO } from '../models/payment.model';
import { AuthService } from '../../auth/auth.service';
import { BookingPaymentResponseDTO } from '../models/payment.model';
import { InvoiceResponseDTO } from '../models/payment.model';

@Injectable({
  providedIn: 'root'
})

export class PaymentService {

    constructor(private http: HttpClient,private authService: AuthService) {}

    private apiUrl='http://localhost:8080/payments/admin';


    private getAllPayemntsUrl = 'http://localhost:8080/payments/admin';

    private updatePaymentUrl='http://localhost:8080/payments/update/';

    private bookingWithPaymentsUrl='http://localhost:8080/payments/admin/bookingwithpayments';

    private userWithPaymentsUrl='http://localhost:8080/payments/admin/userwithpayments';

    private getAllInvoicesUrl='http://localhost:8080/invoice/admin/'
    
    private deleteInvoiceUrl='http://localhost:8080/invoice/admin/'

    private getInvoiceByUserIdUrl='http://localhost:8080/invoice/admin/user'

    private getInvoiceByBookingIdUrl='http://localhost:8080/invoice/admin/booking'

    private getPaymentsByUserIdUrl='http://localhost:8080/payments/'

    private getPaymentByPaymentIdUrl='http://localhost:8080/payments/'

    private getPaymentByBookingIdUrl='http://localhost:8080/payments/admin/booking/'

    private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getAllPayments(): Observable<PaymentResponseDTO[]> {
    return this.http.get<PaymentResponseDTO[]>(`${this.getAllPayemntsUrl}`, { headers: this.getHeaders() });
  }

   getPaymentById(paymentId: number): Observable<PaymentResponseDTO> {
    return this.http.get<PaymentResponseDTO>(`${this.apiUrl}/${paymentId}`, { headers: this.getHeaders() });
  }

  //  getPaymentsByUserId(userId: number): Observable<PaymentResponseDTO[]> {
  //   return this.http.get<PaymentResponseDTO[]>(`${this.apiUrl}/user/${userId}`, { headers: this.getHeaders() });
  // }

   updatePayment(paymentId: number, updateReq: any): Observable<PaymentResponseDTO> {
    return this.http.put<PaymentResponseDTO>(`${this.updatePaymentUrl}/${paymentId}`, updateReq, { headers: this.getHeaders() });
  }

  deletePayment(paymentId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${paymentId}`, { headers: this.getHeaders() });
  }

  getAllBookingWithPayments(): Observable<BookingPaymentResponseDTO[]> {
  return this.http.get<BookingPaymentResponseDTO[]>(`${this.bookingWithPaymentsUrl}`,{ headers: this.getHeaders() } );
}

getAllUserWithPayments(): Observable<UserPaymentResponseDTO[]> {
  return this.http.get<UserPaymentResponseDTO[]>(`${this.userWithPaymentsUrl}`,{ headers: this.getHeaders() } );
}

getAllInvoices(): Observable<InvoiceResponseDTO[]> {
  return this.http.get<InvoiceResponseDTO[]>(`${this.getAllInvoicesUrl}`,{ headers: this.getHeaders() } );
}

deleteInvoice(invoiceId: number): Observable<void> {
    return this.http.delete<void>(`${this.deleteInvoiceUrl}/${invoiceId}`,{ headers: this.getHeaders() });
  }

getInvoiceByUserId(userId: number): Observable<InvoiceResponseDTO[]> {
    return this.http.get<InvoiceResponseDTO[]>(`${this.getInvoiceByUserIdUrl}/${userId}`,{ headers: this.getHeaders() });
  }
  
  getInvoiceByBookingId(bookingId: number): Observable<InvoiceResponseDTO[]> {
      return this.http.get<InvoiceResponseDTO[]>(`${this.getInvoiceByBookingIdUrl}/${bookingId}`,{ headers: this.getHeaders() });
    }
  
getPaymentsByUserId(userId: number): Observable<PaymentResponseDTO[]> {
  return this.http.get<PaymentResponseDTO[]>(`${this.getPaymentsByUserIdUrl}/getpayments/${userId}`);
}



getPaymentByPaymentId(paymentId: number): Observable<PaymentResponseDTO> {
  return this.http.get<PaymentResponseDTO>(`${this.getPaymentByPaymentIdUrl}/${paymentId}`);
}

getPaymentByBookingId(bookingId: number): Observable<PaymentResponseDTO> {
  return this.http.get<PaymentResponseDTO>(`${this.getPaymentByBookingIdUrl}/${bookingId}`);
}
}