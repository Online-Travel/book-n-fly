package onlineTravelBooking.payment_service.service;

import feign.FeignException;
import onlineTravelBooking.payment_service.dto.*;
import onlineTravelBooking.payment_service.entity.Invoice;
import onlineTravelBooking.payment_service.entity.Payment;
import onlineTravelBooking.payment_service.exception.BookingNotFoundException;
import onlineTravelBooking.payment_service.exception.PaymentNotFoundException;
import onlineTravelBooking.payment_service.exception.UserNotFoundException;
import onlineTravelBooking.payment_service.feignclient.BookingServiceClient;
import onlineTravelBooking.payment_service.feignclient.UserServiceClient;
import onlineTravelBooking.payment_service.repository.PaymentRepository;
import onlineTravelBooking.payment_service.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private BookingServiceClient bookingServiceClient;
    
    @Autowired
    private JwtUtil jwtUtil;

    public PaymentResponseDTO savePayment(String token,PaymentRequestDTO paymentRequestDTO,Long userId){
        Payment exist=paymentRepository.findByBookingIdAndUserId(paymentRequestDTO.getBookingId(),userId);
        System.out.println("Save payment");
        if(exist!=null){
            throw new PaymentNotFoundException("Booking Id and userId is already found");
        }
        Payment payment=new Payment();
        payment.setUserId(userId);
        payment.setBookingId(paymentRequestDTO.getBookingId());
        payment.setAmount(paymentRequestDTO.getAmount());
        payment.setStatus("SUCCESS");
        payment.setPaymentMethod(paymentRequestDTO.getPaymentMethod());

        Invoice invoice=new Invoice();
        invoice.setBookingId(paymentRequestDTO.getBookingId());
        invoice.setUserId(userId);
        invoice.setTotalAmount(paymentRequestDTO.getAmount());
        invoice.setTimeStamp(LocalDateTime.now());
        invoice.setPayment(payment);

        payment.setInvoice(invoice);

        Payment saved=paymentRepository.save(payment);

        BookingDTO booking=bookingServiceClient.getBookingById(token,saved.getBookingId());

        booking.setStatus(saved.getStatus());
        booking.setPaymentId(saved.getPaymentId());

        UpdateBookingRequestDTO updateBookingRequestDTO=new UpdateBookingRequestDTO();
        updateBookingRequestDTO.setStatus(payment.getStatus());
        updateBookingRequestDTO.setPaymentId(payment.getPaymentId());

        bookingServiceClient.updateBooking(token,booking.getBookingId(), updateBookingRequestDTO);

        PaymentResponseDTO response=new PaymentResponseDTO();
        response.setPaymentId(saved.getPaymentId());
        response.setUserId(saved.getUserId());
        response.setBookingId(saved.getBookingId());
        response.setAmount(saved.getAmount());
        response.setStatus(saved.getStatus());
        response.setPaymentMethod(saved.getPaymentMethod());

        return response;
    }


    public PaymentResponseDTO updatePayment(Long paymentId, UpdatePaymentRequestDTO updatePaymentRequestDTO){
        Payment payment=paymentRepository.findById(paymentId)
                .orElseThrow(()-> new PaymentNotFoundException("payment id not found"));
        payment.setStatus(updatePaymentRequestDTO.getStatus());
        payment.setPaymentMethod(updatePaymentRequestDTO.getPaymentMethod());

        Payment saved=paymentRepository.save(payment);

        PaymentResponseDTO response=new PaymentResponseDTO();
        response.setPaymentId(saved.getPaymentId());
        response.setUserId(saved.getUserId());
        response.setBookingId(saved.getBookingId());
        response.setAmount(saved.getAmount());
        response.setStatus(saved.getStatus());
        response.setPaymentMethod(saved.getPaymentMethod());

        return  response;
    }

    public List<PaymentResponseDTO> getAllPayments(){
        List<Payment> paymentList=paymentRepository.findAll();

        return paymentList.stream().map(payment -> {
            PaymentResponseDTO dto = new PaymentResponseDTO();
            dto.setPaymentId(payment.getPaymentId());
            dto.setUserId(payment.getUserId());
            dto.setBookingId(payment.getBookingId());
            dto.setAmount(payment.getAmount());
            dto.setStatus(payment.getStatus());
            dto.setPaymentMethod(payment.getPaymentMethod());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<PaymentResponseDTO> getAllPaymentsByUserId(Long userId) {
        List<Payment> paymentList=paymentRepository.findByUserId(userId);

        if(paymentList.isEmpty()){
            throw new PaymentNotFoundException("No payment found for the specific id");
        }

        return paymentList.stream().map(payment -> {
            PaymentResponseDTO dto = new PaymentResponseDTO();
            dto.setPaymentId(payment.getPaymentId());
            dto.setUserId(payment.getUserId());
            dto.setBookingId(payment.getBookingId());
            dto.setAmount(payment.getAmount());
            dto.setStatus(payment.getStatus());
            dto.setPaymentMethod(payment.getPaymentMethod());
            return dto;
        }).collect(Collectors.toList());

    }

    public PaymentResponseDTO getPaymentByPaymentId(Long paymentId) {
        Payment payment=paymentRepository.findById(paymentId).orElseThrow(()->new PaymentNotFoundException("Payment not found with Id"+paymentId));

        PaymentResponseDTO dto=new PaymentResponseDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setUserId(payment.getUserId());
        dto.setBookingId(payment.getBookingId());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setPaymentMethod(payment.getPaymentMethod());
        return dto;

    }

    public PaymentResponseDTO getPaymentByBookingId(Long bookingId) {
        Payment payment=paymentRepository.findByBookingId(bookingId);

        if(payment==null){
            throw  new PaymentNotFoundException("Payment not found with bookingId "+bookingId);
        }

        PaymentResponseDTO dto=new PaymentResponseDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setUserId(payment.getUserId());
        dto.setBookingId(payment.getBookingId());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setPaymentMethod(payment.getPaymentMethod());
        return dto;
    }

    public void deletePaymentByPaymentId(Long paymentId) {
        Optional<Payment> payment=paymentRepository.findById(paymentId);
        if(payment.isEmpty()){
            throw  new PaymentNotFoundException("payment is not found with "+paymentId);
        }
        paymentRepository.deleteById(paymentId);
    }

    public List<PaymentResponseDTO> getAllPaymentForUser(Long userId) {
        List<Payment> paymentList=paymentRepository.findByUserId(userId);
        if(paymentList.isEmpty()){
            throw new PaymentNotFoundException("payment not with id"+userId);
        }
        List<PaymentResponseDTO> dto=new ArrayList<>();
        for (Payment payment : paymentList) {
            dto.add(mapToDTO(payment));
        }
        return dto;
    }

    private PaymentResponseDTO mapToDTO(Payment payment){
        return new PaymentResponseDTO(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getBookingId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getPaymentMethod()
        );
    }


    public List<UserPaymentResponseDTO> getAllUserWithPayments(String token) {

        List<UserDTO> users=userServiceClient.getAllUsers(token);

        System.out.println(users);

        return users.stream().map(user ->{
            List<Payment> payments=paymentRepository.findByUserId(user.getUserId());

            List<PaymentResponseDTO>  paymentDTO=payments.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toUnmodifiableList());

            return new UserPaymentResponseDTO(user,paymentDTO);
        }).collect(Collectors.toUnmodifiableList());

    }

    public List<BookingPaymentResponseDTO> getAllBookingWithPayments(String token) {
        List<BookingDTO> booking=bookingServiceClient.getAllBookings(token);

        return booking.stream().map(book ->{
            List<Payment> payments=paymentRepository.findByUserId(book.getUserId());

            List<PaymentResponseDTO>  paymentDTO=payments.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toUnmodifiableList());

            return new BookingPaymentResponseDTO(book,paymentDTO);
        }).collect(Collectors.toUnmodifiableList());

    }
}
