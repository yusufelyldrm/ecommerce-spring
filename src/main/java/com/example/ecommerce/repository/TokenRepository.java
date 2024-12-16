package com.example.ecommerce.repository;

import com.example.ecommerce.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token save(Token token);
    Token findTokenByToken(String token);
}
