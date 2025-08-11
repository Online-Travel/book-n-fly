package onlineTravelBooking.review_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class ReviewRequestDTO {

    @NotNull
    private Long hotelId;

    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank
    private String comment;
}
