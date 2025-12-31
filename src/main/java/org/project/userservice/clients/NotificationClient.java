package org.project.userservice.clients;

import lombok.RequiredArgsConstructor;
import org.project.userservice.events.NotificationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class NotificationClient {
    private final RestTemplate restTemplate;

    @Value("${notification.service.base-url}")
    private String notificationServiceBaseUrl;

    public void notifyUser(NotificationEvent notificationEvent) {
        String url = notificationServiceBaseUrl + "/notify";
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> restTemplate.postForEntity(url, notificationEvent, Void.class));
    }
}
