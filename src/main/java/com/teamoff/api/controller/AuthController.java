package com.teamoff.api.controller;

import com.teamoff.api.dto.request.AuthRequestDTO;
import com.teamoff.api.dto.response.TokenResponseDTO;
import com.teamoff.api.model.Auth;
import com.teamoff.api.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody @Valid AuthRequestDTO data){

        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(data.login(), data.password())
        );

        var tokenJWT = tokenService.generateToken((Auth) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenResponseDTO(tokenJWT));
    }
}
