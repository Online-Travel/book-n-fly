package onlineTravelBooking.payment_service.controller;

import com.netflix.discovery.converters.Auto;
import onlineTravelBooking.payment_service.dto.*;
import onlineTravelBooking.payment_service.entity.Payment;
import onlineTravelBooking.payment_service.service.PaymentService;
import onlineTravelBooking.payment_service.utils.JwtUtil;
import org.apache.catalina.User;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RestControllerAdvice
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private JwtUtil jwtUtil;

    //Saving the payment to the database by getting the userId from token
    //Need to communicate with the booking service to update the paymentId in the booking table
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('TRAVELER','TRAVEL AGENT')")
    public ResponseEntity<PaymentResponseDTO> savePayment(@RequestBody PaymentRequestDTO paymentRequestDTO, Authentication authentication){

        UserDTO currentUser=(UserDTO) authentication.getPrincipal();
        Long userId=currentUser.getUserId();
        PaymentResponseDTO response=paymentService.savePayment(paymentRequestDTO,userId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //updating the payment status by paymentId
    @PutMapping("/update/{payment_id}")
    @PreAuthorize("hasAnyRole('TRAVELER','TRAVEL AGENT,'ADMIN')")
    public ResponseEntity<?> updatePayment(@PathVariable("payment_id") Long paymentId,@RequestBody UpdatePaymentRequestDTO updatePaymentRequestDTO){
        return new ResponseEntity<>(paymentService.updatePayment(paymentId, updatePaymentRequestDTO),HttpStatus.OK);
    }

    //getting all the successfull payment for the admin
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments(){
        List<PaymentResponseDTO> response=paymentService.getAllPayments();
        return ResponseEntity.ok(response);
    }

    //get the payment for a specific user by passing the userId in request
    @GetMapping("/admin/getpayements")
    @PreAuthorize("hasAnyRole('ADMIN','TRAVELER','TRAVEL AGENT')")
    public ResponseEntity<?> getAllPaymentsByUserId(@PathVariable("user_id") Long userId){
        List<PaymentResponseDTO> response=paymentService.getAllPaymentsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    //getting payment by paymentId
    @GetMapping("/{payment_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentResponseDTO> getPaymentByPaymentId(@PathVariable("payment_id") Long paymentId){
        PaymentResponseDTO responseDTO=paymentService.getPaymentByPaymentId(paymentId);
        return ResponseEntity.ok(responseDTO);
    }

    //getting the payments for booking for admin
    @GetMapping("/admin/booking/{booking_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentResponseDTO> getPaymentByBookingId(@PathVariable("booking_id") Long bookingId){
        PaymentResponseDTO responseDTO=paymentService.getPaymentByBookingId(bookingId);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/admin/{payment_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePaymentByPaymentId(@PathVariable("payment_id") Long paymentId){
        paymentService.deletePaymentByPaymentId(paymentId);
        return ResponseEntity.ok("payment deleted successfully");
    }

    //getting all the payments for the user using token
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('TRAVELER','TRAVEL AGENT')")
    public ResponseEntity<List<PaymentResponseDTO>> getAllPaymentForUser(Authentication authentication){
        UserDTO currentUser=(UserDTO) authentication.getPrincipal();
        Long userId=currentUser.getUserId();
        List<PaymentResponseDTO> responseDTO=paymentService.getAllPaymentForUser(userId);
        return ResponseEntity.ok(responseDTO);
    }

    //getting all the payment for the specific user
    //Need to configure or communicate with the booking-service
    @GetMapping("/booking/{booking_id}")
    @PreAuthorize("hasAnyRole('TRAVELER','TRAVEL AGENT')")
    public ResponseEntity<PaymentResponseDTO> getAllPaymentsByBookingForUser(@PathVariable("booking_id") Long bookingId){
        PaymentResponseDTO responseDTO=paymentService.getAllPaymentsByBookingForUser(bookingId);
        return ResponseEntity.ok(responseDTO);
    }

    //getting all the users and their payment details for admin
    //Need to communicate with the user-service to get all the user
    @GetMapping("/admin/userwithpayments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserPaymentResponseDTO>> getAllUserWithPayments(){
        List<UserPaymentResponseDTO> response=paymentService.getAllUserWithPayments();
        return ResponseEntity.ok(response);
    }

    //getting all the booking and their payment details
    //Need to communicate with the booking-service to get all the booking
    @GetMapping("/admin/bookingwithpayments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingPaymentResponseDTO>> getAllBookingWithPayments(){
        List<BookingPaymentResponseDTO> response=paymentService.getAllBookingWithPayments();
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/greet")
//    public String greet(){
//        return "Hello rajesh";
//    }
}
