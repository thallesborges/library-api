package dev.thallesborges.library.service;

import dev.thallesborges.library.database.entity.UserEntity;
import dev.thallesborges.library.database.repository.UserRepository;
import dev.thallesborges.library.auth.LoginRequest;
import dev.thallesborges.library.auth.RegisterRequest;
import dev.thallesborges.library.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new RuntimeException("Email already exists");
        }

        String passwordHash = passwordEncoder.encode(registerRequest.password());

        UserEntity newUser = UserEntity.builder()
                .name(registerRequest.name())
                .email(registerRequest.email())
                .password(passwordHash)
                .build();

        UserEntity savedUser = userRepository.save(newUser);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    public void loginUser(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
    }
}
