package com.teamoff.api.service;

import com.teamoff.api.dto.request.TeamRequestDTO;
import com.teamoff.api.model.Team;
import com.teamoff.api.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class TeamService {
    private static TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        TeamService.teamRepository = teamRepository;
    }

    public static ResponseEntity<?> createTeam(TeamRequestDTO data) {
        return new ResponseEntity<>(
                teamRepository.save(new Team(data)),
                HttpStatus.CREATED
        );
    }

    public static ResponseEntity<Object> findTeamById(UUID id) {
        Team team = teamRepository.findById(id).orElse(null);
        if (team == null) {new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    public static List<Team> findAllTeams() {
        return teamRepository.findAll();
    }
}
