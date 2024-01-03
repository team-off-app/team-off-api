package com.teamoff.api.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record EventRequestDTO(
        String title,



        LocalDateTime startDate,


        LocalDateTime endDate,
        String notes,
        @UUID
        @NotBlank
        String userId,
        @NotBlank
        String type

) {
}
