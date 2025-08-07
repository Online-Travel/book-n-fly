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

    public PaymentResponseDTO savePayment(PaymentRequestDTO paymentRequestDTO,Long userId){
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

        try{
            bookingServiceClient.updateBookingWithPaymentId(saved.getBookingId(), saved.getPaymentId());
        }
        catch (FeignException e){
            throw new BookingNotFoundException("Payment saved, but failed to update booking with paymentId");
        }

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
//        try{
//            userServiceClient.getUserById(userId);
//        }
//        catch (FeignException.NotFound e){
//            throw new UserNotFoundException("user not found "+userId);
//        }
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

    public PaymentResponseDTO getAllPaymentsByBookingForUser(Long bookingId) {
        try{
            bookingServiceClient.getBookingById(bookingId);
        }
        catch (FeignException.NotFound e){
            throw new BookingNotFoundException("booking not found with "+bookingId);
        }
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

    public List<UserPaymentResponseDTO> getAllUserWithPayments() {
        List<UserDTO> users=userServiceClient.getAllUsers();

        return users.stream().map(user -> {
            List<Payment> payments = paymentRepository.findByUserId(user.getUserId());
            List<PaymentResponseDTO> paymentDTOs = payments.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());

            UserPaymentResponseDTO response = new UserPaymentResponseDTO();
            response.setUser(user);
            response.setPayments(paymentDTOs);
            return response;
        }).collect(Collectors.toList());
    }

    public List<BookingPaymentResponseDTO> getAllBookingWithPayments() {
        List<BookingDTO> booking=bookingServiceClient.getAllBooking();

        List<BookingPaymentResponseDTO> response=new ArrayList<>();

        for(BookingDTO book:booking){
            Payment payment=paymentRepository.findByBookingId(book.getBookingId());
            PaymentResponseDTO paymentResponseDTO=mapToDTO(payment);
            BookingPaymentResponseDTO bookingPaymentResponseDTO=new BookingPaymentResponseDTO();
            bookingPaymentResponseDTO.setBookingDTO(book);
            bookingPaymentResponseDTO.setPaymentResponseDTO(paymentResponseDTO);
            response.add(bookingPaymentResponseDTO);
        }
        return  response;
    }
}
