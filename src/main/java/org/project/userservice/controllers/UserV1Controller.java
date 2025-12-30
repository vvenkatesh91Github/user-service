package org.project.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.project.userservice.dtos.UserRequestV1DTO;
import org.project.userservice.dtos.UserResponseV1DTO;
import org.project.userservice.models.User;
import org.project.userservice.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserV1Controller {
    private final UserService userService;

    @PostMapping
    public UserResponseV1DTO createUser(@RequestBody UserRequestV1DTO userRequestV1DTO) {
        User createdUser = userService.createUser(userRequestV1DTO.toUser());
        return new UserResponseV1DTO(createdUser);
    }

    @GetMapping("/{id}")
    public UserResponseV1DTO getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return new UserResponseV1DTO(user);
    }

    @GetMapping("/email/{email}")
    public UserResponseV1DTO getUser(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return new UserResponseV1DTO(user);
    }

    @GetMapping
    public List<UserResponseV1DTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream().map(UserResponseV1DTO::new).toList();
    }

    @PutMapping("/{id}")
    public UserResponseV1DTO updateUser(@PathVariable Long id, @RequestBody UserRequestV1DTO userRequestV1DTO) {
        User user = userService.updateUser(id, userRequestV1DTO.toUser());
        return new UserResponseV1DTO(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

