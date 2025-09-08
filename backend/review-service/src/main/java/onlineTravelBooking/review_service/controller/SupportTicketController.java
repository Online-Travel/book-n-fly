package onlineTravelBooking.review_service.controller;

import jakarta.validation.Valid;
import onlineTravelBooking.review_service.dto.SupportTicketRequestDTO;
import onlineTravelBooking.review_service.dto.SupportTicketResponseDTO;
import onlineTravelBooking.review_service.service.SupportTicketService;
import onlineTravelBooking.review_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support-tickets")
public class SupportTicketController {
    private final SupportTicketService ticketService;
    private final JwtUtil jwtUtil;

    @Autowired
    public SupportTicketController(SupportTicketService ticketService, JwtUtil jwtUtil) {
        this.ticketService = ticketService;
        this.jwtUtil = jwtUtil;
    }

    // TRAVELER creates ticket
    @PostMapping
    @PreAuthorize("hasRole('TRAVELER')")
    public SupportTicketResponseDTO createTicket(@RequestHeader("Authorization") String token,
                                                 @Valid @RequestBody SupportTicketRequestDTO dto) {
        Long userId = jwtUtil.extractUserId(token.substring(7).trim());
        dto.setUserId(userId);  // Set userId before saving
        return ticketService.createTicket(dto);
    }

    // TRAVELER views their tickets
    @GetMapping("/my")
    @PreAuthorize("hasRole('TRAVELER')")
    public List<SupportTicketResponseDTO> getMyTickets(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.extractUserId(token.substring(7).trim());
        return ticketService.getTicketsByUser(userId);
    }

    // Get single ticket - allowed for authenticated users
    @GetMapping("/{ticketId}")
    @PreAuthorize("isAuthenticated()")
    public SupportTicketResponseDTO getTicket(@PathVariable Long ticketId) {
        return ticketService.getTicket(ticketId);
    }

    // TRAVELER updates their ticket
    @PutMapping("/{ticketId}")
    @PreAuthorize("hasRole('TRAVELER')")
    public SupportTicketResponseDTO updateTicket(@RequestHeader("Authorization") String token,
                                                 @PathVariable Long ticketId,
                                                 @Valid @RequestBody SupportTicketRequestDTO dto) {
        Long userId = jwtUtil.extractUserId(token);
        dto.setUserId(userId);
        return ticketService.updateTicket(userId, ticketId, dto);
    }


    // TRAVELER deletes ticket
    @DeleteMapping("/{ticketId}")
    @PreAuthorize("hasRole('TRAVELER')")
    public void deleteTicket(@RequestHeader("Authorization") String token,
                             @PathVariable Long ticketId) {
        Long userId = jwtUtil.extractUserId(token);
        ticketService.deleteTicket(userId, ticketId);
    }

    // ADMIN views all tickets
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SupportTicketResponseDTO> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // ADMIN views open tickets
    @GetMapping("/open")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SupportTicketResponseDTO> getOpenTickets() {
        return ticketService.getOpenTickets();
    }

    // ADMIN assigns ticket to agent
    @PutMapping("/{ticketId}/assign/{agentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public SupportTicketResponseDTO assignTicket(@PathVariable Long ticketId,
                                                 @PathVariable Long agentId) {
        return ticketService.assignTicket(ticketId, agentId);
    }

    // AGENT closes ticket
    @PutMapping("/{ticketId}/close")
    @PreAuthorize("hasRole('TRAVEL_AGENT')")
    public SupportTicketResponseDTO closeTicket(@PathVariable Long ticketId) {
        return ticketService.closeTicket(ticketId);
    }

}
