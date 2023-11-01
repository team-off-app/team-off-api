package com.teamoff.api.dto;

import jakarta.validation.constraints.NotBlank;

public record TeamDTO(
        @NotBlank
        String name
){
}
