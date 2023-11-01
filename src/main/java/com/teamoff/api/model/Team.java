package com.teamoff.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teamoff.api.dto.request.TeamRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "Team")
@Table(name = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true ,nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private Set<User> user;

    public Team(TeamRequestDTO data) {
        this.name = data.name();
    }
}
