package com.teamoff.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank
        String username,
        @NotBlank
        String photoUrl,
        String team
){
}
