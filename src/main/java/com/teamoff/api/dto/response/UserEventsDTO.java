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
    private String username;
    private String photoUrl;
    private Set<Team> team;
    private List<EventResponseDTO> events;

    public UserEventsDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.photoUrl = user.getPhotoUrl();
        this.team = user.getTeam();
    }
}
