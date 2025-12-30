package org.project.userservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.project.userservice.models.User;

@Getter
@Setter
public class UserRequestV1DTO {
    private String name;
    private String email;
    private String hashedPassword;

    public User toUser() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setHashedPassword(this.hashedPassword);
        return user;
    }
}
