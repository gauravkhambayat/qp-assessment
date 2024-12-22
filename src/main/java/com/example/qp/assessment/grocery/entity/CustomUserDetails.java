package com.example.qp.assessment.grocery.entity;
import com.example.qp.assessment.grocery.service.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.UUID;

public class CustomUserDetails extends UserDetailsImpl {

    //private final UserDetails userDetails;
    private final UUID userId;

    public CustomUserDetails(User user) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
        this.userId = user.getId(); // You can add more custom fields here if needed
    }

 /*   public UserDetails getUserDetails() {
        return userDetails;
    }*/

    public UUID getUserId() {
        return userId;
    }
}
