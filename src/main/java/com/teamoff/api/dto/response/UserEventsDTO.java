package com.teamoff.api.dto.response;

import com.teamoff.api.model.Team;
import com.teamoff.api.model.User;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class UserEventsDTO {
    private UUID id;
    private String name;
    private String photoUrl;
    private Set<Team> teams;
    private List<EventResponseDTO> events;

    public UserEventsDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.photoUrl = user.getPhotoUrl();
        this.teams = user.getTeams();
    }
}
