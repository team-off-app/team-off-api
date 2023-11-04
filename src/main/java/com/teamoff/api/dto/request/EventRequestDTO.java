package com.teamoff.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;

public record EventRequestDTO(
        String title,

        LocalDateTime startDate,

        LocalDateTime endDate,
        String notes,
        @UUID
        String userId,
        @NotBlank
        String type

) {
}
