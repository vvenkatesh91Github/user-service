package org.project.userservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.project.userservice.models.User;

@Getter
@Setter
public class UserRequestV2DTO {
    private String name;
    private String email;
    private String hashedPassword;
    private String phoneNumber;

    public User toUser() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setHashedPassword(this.hashedPassword);
        user.setPhoneNumber(this.phoneNumber);
        return user;
    }
}
