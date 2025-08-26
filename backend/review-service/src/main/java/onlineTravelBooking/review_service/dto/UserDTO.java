package onlineTravelBooking.review_service.dto;

public class UserDTO {

    private Long userId;
    private String email;
    private String name;
    private String role;
    // Assuming you store role

    // Getters and Setters
    public Long getUserId() {

        return userId;
    }
    public void setUserId(Long id) {

        this.userId = id;
    }
    public String getEmail() {

        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {

        return name;
    }
    public void setName(String name) {

        this.name = name;
    }
    public String getRole() {

        return role;
    }
    public void setRole(String role) {

        this.role = role;
    }
}
