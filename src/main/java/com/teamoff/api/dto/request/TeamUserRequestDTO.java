package com.teamoff.api.dto.request;

import org.hibernate.validator.constraints.UUID;

public record TeamUserRequestDTO (
        @UUID
        String userId
) {}
