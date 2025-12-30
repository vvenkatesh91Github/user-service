package org.project.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.project.userservice.dtos.UserRequestV2DTO;
import org.project.userservice.dtos.UserResponseV2DTO;
import org.project.userservice.models.User;
import org.project.userservice.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/users")
@RequiredArgsConstructor
public class UserV2Controller {
    private final UserService userService;

    @PostMapping
    public UserResponseV2DTO createUser(@RequestBody UserRequestV2DTO UserRequestV2DTO) {
        User createdUser = userService.createUser(UserRequestV2DTO.toUser());
        return new UserResponseV2DTO(createdUser);
    }

    @GetMapping("/{id}")
    public UserResponseV2DTO getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return new UserResponseV2DTO(user);
    }

    @GetMapping("/email/{email}")
    public UserResponseV2DTO getUser(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return new UserResponseV2DTO(user);
    }

    @GetMapping("/phone/{phoneNumber}")
    public UserResponseV2DTO getUserByPhoneNumber(@PathVariable String phoneNumber) {
        User user = userService.getUserByPhoneNumber(phoneNumber);
        return new UserResponseV2DTO(user);
    }

    @GetMapping
    public List<UserResponseV2DTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream().map(UserResponseV2DTO::new).toList();
    }

    @PutMapping("/{id}")
    public UserResponseV2DTO updateUser(@PathVariable Long id, @RequestBody UserRequestV2DTO UserRequestV2DTO) {
        User user = userService.updateUser(id, UserRequestV2DTO.toUser());
        return new UserResponseV2DTO(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
