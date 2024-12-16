package com.example.ecommerce.models;

import com.example.ecommerce.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "User id", example = "1", required = true)
    private Integer id;


    @Schema(description = "User email", example = "yusufelyildirim@gmail.com", required = true)
    @Column(nullable = false, unique = true)
    private String email;


    @Schema(description = "User username", example = "user", required = true)
    private String username;

    @Schema(description = "User password", example = "password", required = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Schema(description = "User role", example = "USER", required = true)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Cart", required = true)
    private Cart cart;

    @Column(name = "created_at", updatable = false)
    @Schema(description = "Created at", example = "2021-07-01T00:00:00", required = true)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Token token;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
