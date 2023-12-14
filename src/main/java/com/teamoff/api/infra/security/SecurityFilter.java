package com.teamoff.api.infra.security;
import com.teamoff.api.model.Auth;
import com.teamoff.api.model.User;
import com.teamoff.api.repository.AuthRepository;
import com.teamoff.api.repository.UserRepository;
import com.teamoff.api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository, AuthRepository authRepository) {
        this.tokenService = tokenService;
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = getToken(request);
        if (tokenJWT != null) {
            String subject = tokenService.getSubject(tokenJWT);
            Auth auth = authRepository.findByUser(UUID.fromString(subject));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(auth, null, auth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}
