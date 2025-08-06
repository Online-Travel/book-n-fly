package onlineTravelBooking.payment_service.controller;

import onlineTravelBooking.payment_service.dto.InvoiceResponseDTO;
import onlineTravelBooking.payment_service.service.EmailSenderService;
import onlineTravelBooking.payment_service.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {


    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/mail/{invoice_id}")
    public ResponseEntity<String> sendMail(@PathVariable("invoice_id") Long invoiceId){
      String result=invoiceService.sendMail(invoiceId);
      return ResponseEntity.ok(result);
    }

    @GetMapping("/admin/{user_id}")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoiceByUserId(@PathVariable("user_id") Long userId){
        List<InvoiceResponseDTO> response=invoiceService.getAllInvoiceByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/{booking_id}")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoicesByBookingId(@PathVariable("booking_id") Long bookingId){
        List<InvoiceResponseDTO> response=invoiceService.getAllInvoiceByBookingId(bookingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices(){
        List<InvoiceResponseDTO> response=invoiceService.getAllInvoices();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/{invoice_id}")
    public ResponseEntity<String> deleteInvoiceById(@PathVariable("invoice_id")Long invoiceId){
        return ResponseEntity.ok(invoiceService.deleteInvoiceById(invoiceId));

    }

    @GetMapping("/admin/payment/{payment_id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceByPaymentId(@PathVariable("payment_id") Long paymentId){
        InvoiceResponseDTO response=invoiceService.getInvoiceByPaymentId(paymentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/greet")
    public String greet(){
        return "hello";
    }

}
