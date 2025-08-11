package onlineTravelBooking.review_service.service;

import onlineTravelBooking.review_service.dto.SupportTicketRequestDTO;
import onlineTravelBooking.review_service.dto.SupportTicketResponseDTO;
import onlineTravelBooking.review_service.entity.SupportTicket;
import onlineTravelBooking.review_service.repository.SupportTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupportTicketServiceImpl implements SupportTicketService {

    private final SupportTicketRepository repo;

    @Autowired
    public SupportTicketServiceImpl(SupportTicketRepository repo) {
        this.repo = repo;
    }

    @Override
    public SupportTicketResponseDTO createTicket(SupportTicketRequestDTO dto) {
        SupportTicket ticket = new SupportTicket();
        ticket.setUserId(dto.getUserId());
        ticket.setIssue(dto.getIssue());
        ticket.setStatus("OPEN");
        ticket.setCreatedAt(LocalDateTime.now());

        return toDTO(repo.save(ticket));
    }

    @Override
    public List<SupportTicketResponseDTO> getTicketsByUser(Long userId) {
        return repo.findByUserId(userId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public SupportTicketResponseDTO getTicket(Long ticketId) {
        return toDTO(repo.findById(ticketId).orElseThrow());
    }

    @Override
    public SupportTicketResponseDTO updateTicket(Long userId, Long ticketId, SupportTicketRequestDTO dto) {
        SupportTicket ticket = repo.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        if (!ticket.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this ticket.");
        }

        ticket.setIssue(dto.getIssue());

        return toDTO(repo.save(ticket));
    }

    @Override
    public void deleteTicket(Long userId, Long ticketId) {
        SupportTicket ticket = repo.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));

        if (!ticket.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this ticket.");
        }

        repo.delete(ticket);
    }

    @Override
    public List<SupportTicketResponseDTO> getAllTickets() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<SupportTicketResponseDTO> getOpenTickets() {
        return repo.findByStatus("OPEN").stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public SupportTicketResponseDTO assignTicket(Long ticketId, Long agentId) {
        SupportTicket ticket = repo.findById(ticketId).orElseThrow();
        ticket.setAssignedAgentId(agentId);
        ticket.setStatus("IN_PROGRESS");
        return toDTO(repo.save(ticket));
    }

    @Override
    public SupportTicketResponseDTO closeTicket(Long ticketId) {
        SupportTicket ticket = repo.findById(ticketId).orElseThrow();
        ticket.setStatus("CLOSED");
        return toDTO(repo.save(ticket));
    }

    @Override
    public List<SupportTicketResponseDTO> getAssignedTickets(Long agentId) {
        return repo.findByAssignedAgentId(agentId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    private SupportTicketResponseDTO toDTO(SupportTicket t) {
        SupportTicketResponseDTO dto = new SupportTicketResponseDTO();
        dto.setTicketId(t.getTicketId());
        dto.setUserId(t.getUserId());
        dto.setIssue(t.getIssue());
        dto.setStatus(t.getStatus());
        dto.setAssignedAgentId(t.getAssignedAgentId());
        dto.setCreatedAt(t.getCreatedAt());
        return dto;
    }
}
