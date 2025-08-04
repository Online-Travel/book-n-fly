package onlineTravelBooking.payment_service.exception;

import onlineTravelBooking.payment_service.dto.UserDTO;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
