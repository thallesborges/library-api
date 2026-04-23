package dev.thallesborges.library.admin;

import dev.thallesborges.library.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

}
