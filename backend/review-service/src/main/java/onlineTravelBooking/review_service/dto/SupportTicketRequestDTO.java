package onlineTravelBooking.review_service.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupportTicketRequestDTO {

    private Long userId;

    @NotBlank
    private String issue;
}
