package com.teamoff.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record UserAuthRequestDTO(
        @NotBlank
        String name,
        String photoUrl,
        @Email
        String login,
        String password
){
}
