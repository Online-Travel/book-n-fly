import { PaymentService } from './../../services/payment.service';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';


declare var Razorpay: any;

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent {
  constructor(private paymentService: PaymentService) {}

  pay(amount: number) {
    this.paymentService.createOrder(amount).subscribe((order: any) => {
      const options = {
        key: 'rzp_test_m17StH2RvJgYRu', // Replace with your Razorpay Key ID
        amount: order.amount,
        currency: order.currency,
        name: 'My App',
        description: 'Test Transaction',
        order_id: order.id,
        handler: function (response: any) {
          alert("Payment successful. Payment ID: " + response.razorpay_payment_id);
        },
        prefill: {
          name: 'Test User',
          email: 'test@example.com',
          contact: '9999999999'
        },
        theme: {
          color: '#3399cc'
        }
      };

      const rzp = new Razorpay(options);
      rzp.open();
    });
  }
}
