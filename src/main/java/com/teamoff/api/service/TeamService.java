package com.teamoff.api.service;

import com.teamoff.api.dto.request.TeamRequestDTO;
import com.teamoff.api.model.Event;
import com.teamoff.api.model.Team;
import com.teamoff.api.repository.EventRepository;
import com.teamoff.api.repository.TeamRepository;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final EventRepository eventRepository;

    public TeamService(TeamRepository teamRepository, EventRepository eventRepository) {
        this.teamRepository = teamRepository;
        this.eventRepository = eventRepository;
    }

    public ResponseEntity<?> createTeam(TeamRequestDTO data) {
        return new ResponseEntity<>(
                teamRepository.save(new Team(data)),
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> findTeamById(UUID id) {
        Team team = teamRepository.findById(id).orElse(null);
        if (team == null) {new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    public List<Event> getTeamEvents(UUID id, LocalDateTime startDate, LocalDateTime endDate) {
        var team = teamRepository.findById(id).orElseThrow(NullPointerException::new);
        return eventRepository.findAllTeamEventsBetweenDates(team, startDate, endDate);
    }
}
