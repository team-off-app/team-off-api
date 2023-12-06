package com.teamoff.api.service;

import com.teamoff.api.dto.request.TeamRequestDTO;
import com.teamoff.api.dto.response.UserEventsDTO;
import com.teamoff.api.model.Team;
import com.teamoff.api.repository.EventRepository;
import com.teamoff.api.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final EventRepository eventRepository;
    private final EventService eventService;

    public TeamService(TeamRepository teamRepository, EventRepository eventRepository, EventService eventService) {
        this.teamRepository = teamRepository;
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }

    public Team createTeam(TeamRequestDTO data) {
        return teamRepository.save(new Team(data));
    }

    public ResponseEntity<Object> findTeamById(UUID id) {
        Team team = teamRepository.findById(id).orElse(null);
        if (team == null) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    public List<UserEventsDTO> getTeamEvents(UUID id, LocalDateTime startDate, LocalDateTime endDate) {
        Team team = teamRepository.findById(id).orElseThrow(NullPointerException::new);
        return eventService.groupEventsByUser(eventRepository.findAllTeamEventsBetweenDates(team, startDate, endDate));
    }
}
