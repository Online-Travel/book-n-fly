package onlineTravelBooking.payment_service.feignclient;

import onlineTravelBooking.payment_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/user/{user_id}")
    UserDTO getUserById(@PathVariable("user_id") Long userId);

    @GetMapping("/user")
    List<UserDTO> getAllUsers();


}
