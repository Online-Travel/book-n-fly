package onlineTravelBooking.payment_service.exception;

public class InvoiceNotFoundException extends RuntimeException{
    public InvoiceNotFoundException(String message){
        super(message);
    }
}
