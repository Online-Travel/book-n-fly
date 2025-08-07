package onlineTravelBooking.payment_service.feignclient;

import onlineTravelBooking.payment_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="LOGIN-SERVICE")
public interface UserServiceClient {

    @GetMapping("/admin/user/{id}")
    UserDTO getUserById(@PathVariable("id") Long userId);

    @GetMapping("/user")
    List<UserDTO> getAllUsers();


}
