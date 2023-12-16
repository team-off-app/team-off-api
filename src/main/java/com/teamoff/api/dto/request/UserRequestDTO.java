package com.teamoff.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String photoUrl,
        String team
){
}
