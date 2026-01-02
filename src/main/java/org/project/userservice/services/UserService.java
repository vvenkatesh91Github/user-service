package org.project.userservice.services;

import lombok.RequiredArgsConstructor;
import net.spy.memcached.MemcachedClient;
import org.project.userservice.clients.NotificationClient;
import org.project.userservice.events.*;
import org.project.userservice.models.User;
import org.project.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MemcachedClient memcachedClient;
    private final NotificationClient notificationClient;
    private final NotificationEventProducer eventProducer;

    private static final String USERS_LIST_KEY = "users:list";
    private static final int CACHE_TTL_SECONDS = 30;

    public User createUser(User user) {
        User saved = userRepository.save(user);
        memcachedClient.delete(USERS_LIST_KEY);
        return saved;
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Test user creation notification
        NotificationEvent userCreatedEvent = new UserCreatedEvent(user.getId(), user.getEmail(), user.getPhoneNumber());
//        Commented sync notification to use async event-driven approach
//        notificationClient.notifyUser(userCreatedEvent);
        eventProducer.publish(userCreatedEvent);
        return user;
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        try {
            Object cached = memcachedClient.get(USERS_LIST_KEY);
            if (cached != null) {
                return (List<User>) cached;
            }
        } catch (Exception e) {
            // Log memcached read error
        }
        List<User> users = userRepository.findAll();

        try {
            memcachedClient.set(USERS_LIST_KEY, CACHE_TTL_SECONDS, users);
        } catch (Exception e) {
            // Log memcached write error
        }

        return users;
    }

    public User updateUser(Long id, User user) {
        User existing = getUser(id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setRoles(user.getRoles());
        existing.setStatus(user.getStatus());
        existing.setUpdatedAt(LocalDateTime.now());

        User updated = userRepository.save(existing);
        memcachedClient.delete(USERS_LIST_KEY);
        return updated;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        memcachedClient.delete(USERS_LIST_KEY);
    }
}

