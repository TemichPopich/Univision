package com.example.auth_service.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class RequestFilter extends OncePerRequestFilter {
    private final JwtConfig config;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return; // или обработать отсутствие токена
        }

        String jwt = authHeader.substring(7); // "Bearer ".length() = 7
        try {
            String username = config.getUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                 var token = new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                config.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).toList()
                 );
                 SecurityContextHolder.getContext().setAuthentication(token);
            }
        } catch (ExpiredJwtException e) {
            log.debug("Token expired: {}", e.getMessage());
        } catch (SignatureException e) {
            log.debug("Invalid token signature: {}", e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request, response);
    }
}
