package dev.thallesborges.library.admin;

import dev.thallesborges.library.auth.RegisterRequest;
import dev.thallesborges.library.exception.EmailAlreadyExistsException;
import dev.thallesborges.library.user.UserEntity;
import dev.thallesborges.library.user.UserRepository;
import dev.thallesborges.library.user.UserResponse;
import dev.thallesborges.library.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()))
                .toList();
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.ADMIN)
                .build();

        userRepository.save(userEntity);

        return new UserResponse(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getRole()
        );
    }
}
