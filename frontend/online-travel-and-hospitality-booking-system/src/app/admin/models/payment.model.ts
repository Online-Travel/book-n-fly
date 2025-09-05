export interface PaymentResponseDTO {
  paymentId: number;
  userId: number;
  bookingId: number;
  amount: number;
  status: string;
  paymentMethod: string;
}


export interface UpdatePaymentRequestDTO {
  status: string;
  paymentMethod: string;
}

export interface BookingDTO{
   bookingId:number;
    userId:number;
    type:string;
    status:string;
    paymentId:number;
}

export interface BookingPaymentResponseDTO {
  bookingDTO: BookingDTO;
  paymentResponseDTO: PaymentResponseDTO[];
}

export interface UserDTO{
   userId:number;
   name:string;
   Email:string;
   password:string;
   role:string;
   contactNumber:number;
}

export interface UserPaymentResponseDTO{
  user:UserDTO;
  payments:PaymentResponseDTO[];
}

export interface InvoiceResponseDTO{
 invoiceId:number;
 bookingId:number;
 userId:number;
 amount:number;
 timestamp:Date;
 
}