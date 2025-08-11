package onlineTravelBooking.review_service.service;

import onlineTravelBooking.review_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="LOGIN-SERVICE")
public interface UserClient {
    @GetMapping("/admin/user/{id}")
    UserDTO getUserById(@PathVariable("id") Long userId);
}
