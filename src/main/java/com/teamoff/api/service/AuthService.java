package com.teamoff.api.service;

import com.teamoff.api.repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        var auth = authRepository.findByLogin(username);
        if (auth != null) {
            return auth;
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }
}
