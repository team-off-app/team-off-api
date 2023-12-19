package com.teamoff.api.service;

import com.teamoff.api.dto.request.UserAuthRequestDTO;
import com.teamoff.api.model.Auth;
import com.teamoff.api.model.User;
import com.teamoff.api.repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final AuthRepository authRepository;

    private final PasswordEncoder encoder;




    public AuthService(AuthRepository authRepository, PasswordEncoder encoder) {
        this.authRepository = authRepository;
        this.encoder = encoder;
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

    public Auth createAuth(UserAuthRequestDTO data, User user) {
        return authRepository.save(new Auth(data.login(), encoder.encode(data.password()), user));
    }


}
