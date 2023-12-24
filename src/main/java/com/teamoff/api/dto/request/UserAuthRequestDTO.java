package com.teamoff.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record UserAuthRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String photoUrl,
        @Email
        String login,
        String password
){
}
