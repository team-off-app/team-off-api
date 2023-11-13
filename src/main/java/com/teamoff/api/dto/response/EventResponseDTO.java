package com.teamoff.api.dto.response;

import com.teamoff.api.model.Event;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record EventResponseDTO(
        UUID id,
        String title,
        String startDate,
        String endDate,
        String notes,
        String type
) {
    public EventResponseDTO(Event e) {
        this(
                e.getId(),
                e.getTitle(),
                e.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                e.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                e.getNotes(),
                String.valueOf(e.getType())
        );
    }

}
