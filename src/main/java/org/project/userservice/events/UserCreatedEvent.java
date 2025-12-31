package org.project.userservice.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCreatedEvent implements NotificationEvent {
    private Long userId;
    private String email;
    private String phoneNumber;

    @Override
    public String getEventType() {
        return "USER_CREATED";
    }
}
