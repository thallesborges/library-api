package dev.thallesborges.library.admin;

import dev.thallesborges.library.auth.RegisterRequest;
import dev.thallesborges.library.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return adminService.register(request);
    }

}
