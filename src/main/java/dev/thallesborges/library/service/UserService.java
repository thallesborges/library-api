package dev.thallesborges.library.service;

import dev.thallesborges.library.database.entity.UserEntity;
import dev.thallesborges.library.database.repository.UserRepository;
import dev.thallesborges.library.dto.LoginUserRequest;
import dev.thallesborges.library.dto.RegisterUserRequest;
import dev.thallesborges.library.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse registerUser(RegisterUserRequest registerUserRequest) {
        if (userRepository.existsByEmail(registerUserRequest.email())) {
            throw new RuntimeException("Email already exists");
        }

        String passwordHash = passwordEncoder.encode(registerUserRequest.password());

        UserEntity newUser = UserEntity.builder()
                .name(registerUserRequest.name())
                .email(registerUserRequest.email())
                .password(passwordHash)
                .build();

        UserEntity savedUser = userRepository.save(newUser);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    public void loginUser(LoginUserRequest loginUserRequest) {
        UserEntity user = userRepository.findByEmail(loginUserRequest.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginUserRequest.password(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
    }
}
