package dev.thallesborges.library.auth;

import dev.thallesborges.library.exception.EmailAlreadyExistsException;
import dev.thallesborges.library.exception.UserNotFoundException;
import dev.thallesborges.library.exception.WrongPasswordException;
import dev.thallesborges.library.user.UserEntity;
import dev.thallesborges.library.user.UserRepository;
import dev.thallesborges.library.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        UserEntity user = UserEntity.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if  (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new WrongPasswordException("Wrong password");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(token);
    }
}
