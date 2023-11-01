package com.teamoff.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TeamRequestDTO(
        @NotBlank
        String name
){
}
