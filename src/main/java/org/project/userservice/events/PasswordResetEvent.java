package org.project.userservice.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PasswordResetEvent implements NotificationEvent {
    private String email;

    @Override
    public String getEventType() {
        return "PASSWORD_RESET";
    }
}
