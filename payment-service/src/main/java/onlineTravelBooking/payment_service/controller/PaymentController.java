package onlineTravelBooking.payment_service.controller;

import com.netflix.discovery.converters.Auto;
import onlineTravelBooking.payment_service.dto.*;
import onlineTravelBooking.payment_service.entity.Payment;
import onlineTravelBooking.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RestControllerAdvice
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentResponseDTO> savePayment(@RequestBody PaymentRequestDTO paymentRequestDTO){
        PaymentResponseDTO response=paymentService.savePayment(paymentRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/update/{payment_id}")
    public ResponseEntity<?> updatePayment(@PathVariable("payment_id") Long paymentId,@RequestBody UpdatePaymentRequestDTO updatePaymentRequestDTO){
        return new ResponseEntity<>(paymentService.updatePayment(paymentId, updatePaymentRequestDTO),HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments(){
        List<PaymentResponseDTO> response=paymentService.getAllPayments();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/{user_id}")
    public ResponseEntity<?> getAllPaymentsByUserId(@PathVariable("user_id") Long userId){
        List<PaymentResponseDTO> response=paymentService.getAllPaymentsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{payment_id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByPaymentId(@PathVariable("payment_id") Long paymentId){
        PaymentResponseDTO responseDTO=paymentService.getPaymentByPaymentId(paymentId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/admin/booking/{booking_id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByBookingId(@PathVariable("booking_id") Long bookingId){
        PaymentResponseDTO responseDTO=paymentService.getPaymentByBookingId(bookingId);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/admin/{payment_id}")
    public ResponseEntity<String> deletePaymentByPaymentId(@PathVariable("payment_id") Long paymentId){
        paymentService.deletePaymentByPaymentId(paymentId);
        return ResponseEntity.ok("payment deleted successfully");
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<PaymentResponseDTO>> getAllPaymentForUser(@PathVariable("user_id") Long userId){
        List<PaymentResponseDTO> responseDTO=paymentService.getAllPaymentForUser(userId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/booking/{booking_id}")
    public ResponseEntity<PaymentResponseDTO> getAllPaymentsByBookingForUser(@PathVariable("booking_id") Long bookingId){
        PaymentResponseDTO responseDTO=paymentService.getAllPaymentsByBookingForUser(bookingId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/admin/userwithpayments")
    public ResponseEntity<List<UserPaymentResponseDTO>> getAllUserWithPayments(){
        List<UserPaymentResponseDTO> response=paymentService.getAllUserWithPayments();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/bookingwithpayments")
    public ResponseEntity<List<BookingPaymentResponseDTO>> getAllBooingWithPayments(){
        List<BookingPaymentResponseDTO> response=paymentService.getAllBookingWithPayments();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/greet")
    public String greet(){
        return "Hello rajesh";
    }
}
