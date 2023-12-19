package com.teamoff.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String photoUrl,
        String team,
        @Email
        String login,
        String password
){
}
