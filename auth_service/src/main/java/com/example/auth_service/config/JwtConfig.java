package com.example.auth_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Validated
@ConfigurationProperties(prefix = "jwt", ignoreUnknownFields = false)
public record JwtConfig(
        @NotNull Resource publicKey,
        @NotNull Resource privateKey,
        @NotNull Duration lifetime
) {
    public String generateToken(@NotEmpty UserDetails userDetails) throws Exception {
        return Jwts.builder()
                .claims(createClaims(userDetails))
                .signWith(getPrivateKey())
                .compact();
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

    public String getUsername(String token) throws Exception {
        return verifyToken(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token) throws Exception {
        return verifyToken(token).get("roles", List.class);
    }

    private Claims verifyToken(String token) throws Exception {
        return Jwts.parser()
                .verifyWith(getPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private PrivateKey getPrivateKey() throws Exception {
        String key = Files.readString(privateKey.getFile().toPath());
        key = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] data = Base64.getDecoder().decode((key.getBytes()));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(data);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private PublicKey getPublicKey() throws Exception {
        String key = Files.readString(publicKey.getFile().toPath());
        key = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] data = Base64.getDecoder().decode((key.getBytes()));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }
}
