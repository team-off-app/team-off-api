package com.teamoff.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank
        String username,
        @NotBlank
        String photoUrl,
        String team
){
}
