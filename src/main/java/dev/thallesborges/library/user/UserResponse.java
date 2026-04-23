package dev.thallesborges.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserResponse(
        @NotNull
        Long id,

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email
) {}
