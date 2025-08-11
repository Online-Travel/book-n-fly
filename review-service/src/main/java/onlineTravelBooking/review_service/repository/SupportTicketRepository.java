package onlineTravelBooking.review_service.repository;

import onlineTravelBooking.review_service.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket,Long> {
    List<SupportTicket> findByUserId(Long userId);
    List<SupportTicket> findByAssignedAgentId(Long agentId);
    List<SupportTicket> findByStatus(String status);
}
