package onlineTravelBooking.payment_service.service;

import onlineTravelBooking.payment_service.dto.InvoiceResponseDTO;
import onlineTravelBooking.payment_service.dto.UserDTO;
import onlineTravelBooking.payment_service.entity.Invoice;
import onlineTravelBooking.payment_service.exception.InvoiceNotFoundException;
import onlineTravelBooking.payment_service.feignclient.UserServiceClient;
import onlineTravelBooking.payment_service.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserServiceClient userServiceClient;

    public String sendMail(Long invoiceId,String email){
        Optional<Invoice> invoiceOptional=invoiceRepository.findById(invoiceId);
        if(invoiceOptional.isEmpty()){
            throw new InvoiceNotFoundException("No Invoice found with id "+invoiceId);
        }
        Invoice invoice=invoiceOptional.get();
        return emailSenderService.sendMail(email,invoice);
    }

    private InvoiceResponseDTO mapToDto(Invoice invoice){
        InvoiceResponseDTO dto=new InvoiceResponseDTO();
        dto.setInvoiceId(invoice.getInvoiceId());
        dto.setUserId(invoice.getUserId());
        dto.setBookingId(invoice.getBookingId());
        dto.setAmount(invoice.getTotalAmount());
        dto.setTimestamp(invoice.getTimeStamp());
        return dto;
    }
    public List<InvoiceResponseDTO> getAllInvoiceByUserId(Long userId) {
        List<Invoice> saved=invoiceRepository.findAllByUserId(userId);
        if(saved.isEmpty()){
            throw new InvoiceNotFoundException("UserId not found with Id"+userId);
        }
        List<InvoiceResponseDTO> response=new ArrayList<>();
        for(Invoice invoice:saved){
            InvoiceResponseDTO dto=mapToDto(invoice);
            response.add(dto);
        }
        return response;
    }

    public List<InvoiceResponseDTO> getAllInvoiceByBookingId(Long bookingId) {
        List<Invoice> saved=invoiceRepository.findAllByBookingId(bookingId);
        if(saved.isEmpty()){
            throw new InvoiceNotFoundException("BookingId not found with Id"+bookingId);
        }
        List<InvoiceResponseDTO> response=new ArrayList<>();
        for(Invoice invoice:saved){
            InvoiceResponseDTO dto=mapToDto(invoice);
            response.add(dto);
        }
        return response;
    }

    public List<InvoiceResponseDTO> getAllInvoices() {
        List<Invoice> saved=invoiceRepository.findAll();
        List<InvoiceResponseDTO> response=new ArrayList<>();
        for(Invoice invoice:saved){
            InvoiceResponseDTO dto=mapToDto(invoice);
            response.add(dto);
        }
        return  response;
    }

    public String deleteInvoiceById(Long invoiceId) {
        Optional<Invoice> saved=invoiceRepository.findById(invoiceId);
        if(saved.isEmpty()){
            throw new InvoiceNotFoundException("invoiceId is not found");
        }
        Invoice in = saved.get();
        invoiceRepository.deleteById(in.getBookingId());
        return "deleted successfully";
    }

    public InvoiceResponseDTO getInvoiceByPaymentId(Long paymentId) {
        Optional<Invoice> saved=invoiceRepository.findByPayment_PaymentId(paymentId);
        if(saved.isEmpty()){
            throw new InvoiceNotFoundException("Payment not found with the specific invoice");
        }
        Invoice invoice=saved.get();
        InvoiceResponseDTO response=mapToDto(invoice);
        return  response;
    }
}
