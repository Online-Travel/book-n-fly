package onlineTravelBooking.payment_service.feignclient;

import onlineTravelBooking.payment_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name="LOGIN-SERVICE")
public interface UserServiceClient {

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    List<UserDTO> getAllUsers(@RequestHeader("Authorization") String token);

}
