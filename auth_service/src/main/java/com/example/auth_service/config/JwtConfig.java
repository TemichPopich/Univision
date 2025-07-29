package com.example.auth_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import java.security.Key;
import java.security.PublicKey;
import java.time.Duration;
import java.util.Date;
import java.util.List;


// todo написать генерацию токена на основе ассиметричного ключа шифрования
@Validated
@ConfigurationProperties(prefix = "jwt", ignoreUnknownFields = false)
public record JwtConfig(
        @NotEmpty PublicKey secret,
        @NotEmpty Duration lifetime
) {
    public String generateToken(@NotEmpty UserDetails userDetails) {
        return Jwts.builder()
                .claims(createClaims(userDetails))
                .signWith(secret)
                .compact();
    }

    private Key getSigningKey() {
//        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
//        return Keys.hmacShaKeyFor(keyBytes);
        return null;
    }

    private Claims createClaims(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.claims()
                .subject(userDetails.getUsername())
                .add("roles", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + lifetime.toMillis()))
                .build();
    }

    private Claims getClaims(String token) {
//        return Jwts.parser();
        return null;
    }
}
