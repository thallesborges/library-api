package dev.thallesborges.library.database.entity;

import dev.thallesborges.library.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;

    private String email;

    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
}
