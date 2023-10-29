package com.teamoff.api.dto.create;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO (
        @NotBlank
        String username,
        @NotBlank
        String photoUrl,
        String team
){
}
