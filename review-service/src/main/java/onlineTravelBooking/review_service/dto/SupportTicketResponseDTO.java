package onlineTravelBooking.review_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SupportTicketResponseDTO {

    private Long ticketId;
    private Long userId;
    private String issue;
    private String status;
    private Long assignedAgentId;
    private LocalDateTime createdAt;
}
