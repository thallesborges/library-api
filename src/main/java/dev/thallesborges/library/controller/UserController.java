package dev.thallesborges.library.controller;

import dev.thallesborges.library.dto.LoginUserRequest;
import dev.thallesborges.library.dto.RegisterUserRequest;
import dev.thallesborges.library.dto.UserResponse;
import dev.thallesborges.library.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;

    @PostMapping("auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        return userService.registerUser(registerUserRequest);
    }

    @PostMapping("auth/login")
    @ResponseStatus(HttpStatus.OK)
    public void loginUser(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        userService.loginUser(loginUserRequest);
    }

}
