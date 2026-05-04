package dev.thallesborges.library.admin;

import dev.thallesborges.library.auth.RegisterRequest;
import dev.thallesborges.library.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> findAllUsers() {
        return adminService.findAllUsers();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return adminService.register(request);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserResponse deleteUser(@PathVariable Long id) {
        return adminService.delete(id);
    }

}
