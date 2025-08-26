package onlineTravelBooking.payment_service.controller;

import onlineTravelBooking.payment_service.dto.InvoiceResponseDTO;
import onlineTravelBooking.payment_service.dto.UserDTO;
import onlineTravelBooking.payment_service.service.EmailSenderService;
import onlineTravelBooking.payment_service.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
@RestControllerAdvice
public class InvoiceController {


    @Autowired
    private InvoiceService invoiceService;

    //getting the mail from the token and sending the mail
    @PostMapping("/mail/{invoice_id}")
    @PreAuthorize("hasAnyRole('TRAVELER','TRAVEL_AGENT')")
    public ResponseEntity<String> sendMail(@PathVariable("invoice_id") Long invoiceId, Authentication authentication){
        UserDTO currentUser = (UserDTO) authentication.getPrincipal();
        String email = currentUser.getEmail();
        String result=invoiceService.sendMail(invoiceId,email);
        return ResponseEntity.ok(result);
    }

    //getting all the invoices for the admin
    @GetMapping("/admin/user/{user_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoiceByUserId(@PathVariable("user_id") Long userId){
        List<InvoiceResponseDTO> response=invoiceService.getAllInvoiceByUserId(userId);
        return ResponseEntity.ok(response);
    }

    //getting all the invoices for the admin by bookingId
    @GetMapping("/admin/booking/{booking_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoicesByBookingId(@PathVariable("booking_id") Long bookingId){
        List<InvoiceResponseDTO> response=invoiceService.getAllInvoiceByBookingId(bookingId);
        return ResponseEntity.ok(response);
    }

    //getting all the invoices for admin
    @GetMapping("/admin/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices(){
        List<InvoiceResponseDTO> response=invoiceService.getAllInvoices();
        return ResponseEntity.ok(response);
    }

    //delete the invoice by admin
    @DeleteMapping("/admin/{invoice_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteInvoiceById(@PathVariable("invoice_id")Long invoiceId){
        return ResponseEntity.ok(invoiceService.deleteInvoiceById(invoiceId));

    }

    @GetMapping("/admin/payment/{payment_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceByPaymentId(@PathVariable("payment_id") Long paymentId){
        InvoiceResponseDTO response=invoiceService.getInvoiceByPaymentId(paymentId);
        return ResponseEntity.ok(response);
    }


}
