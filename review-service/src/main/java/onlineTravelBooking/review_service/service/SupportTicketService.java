package onlineTravelBooking.review_service.service;

import onlineTravelBooking.review_service.dto.SupportTicketRequestDTO;
import onlineTravelBooking.review_service.dto.SupportTicketResponseDTO;

import java.util.List;

public interface SupportTicketService {
    SupportTicketResponseDTO createTicket(SupportTicketRequestDTO dto);

    List<SupportTicketResponseDTO> getTicketsByUser(Long userId);

    SupportTicketResponseDTO getTicket(Long ticketId);

    SupportTicketResponseDTO updateTicket(Long userId, Long ticketId, SupportTicketRequestDTO dto);

    void deleteTicket(Long userId, Long ticketId);

    List<SupportTicketResponseDTO> getAllTickets();

    List<SupportTicketResponseDTO> getOpenTickets();

    SupportTicketResponseDTO assignTicket(Long ticketId, Long agentId);

    SupportTicketResponseDTO closeTicket(Long ticketId);

}
