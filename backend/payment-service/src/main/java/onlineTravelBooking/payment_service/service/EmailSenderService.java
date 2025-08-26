package onlineTravelBooking.payment_service.service;

import onlineTravelBooking.payment_service.entity.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendMail(String recipientEmail,Invoice invoice){
        try{
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(recipientEmail);
            message.setSubject("Your Invoice - ID: "+invoice.getInvoiceId());

            String body="Dear Customer,\n\n"+
                        "Thank you for booking with us.Here are your invoice details:\n\n"+
                        "Invoice ID: "+invoice.getInvoiceId()+"\n"+
                        "Booking ID: "+invoice.getBookingId()+"\n"+
                        "User ID: "+invoice.getUserId()+"\n"+
                    "Total Amount: "+invoice.getTotalAmount()+"\n"+
                    "Date: "+invoice.getTimeStamp()+"\n\n"+
                    "Regards,\nOnline Travel Booking Teams";

            message.setText(body);
            mailSender.send(message);
            return "mail send successfully";
        }
        catch (Exception e){
            return "Error while Sending mail";
        }
    }
}
