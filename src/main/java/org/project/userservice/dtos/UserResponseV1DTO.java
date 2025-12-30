package org.project.userservice.dtos;

import lombok.Getter;
import org.project.userservice.models.User;

@Getter
public final class UserResponseV1DTO {
    private final Long id;
    private final String name;
    private final String email;
    private final String roles;
    private final String status;
    private final String hashedPassword;

    public UserResponseV1DTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.status = String.valueOf(user.getStatus());
        this.hashedPassword = user.getHashedPassword();
    }
}
