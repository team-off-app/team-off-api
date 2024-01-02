package com.teamoff.api.service;

import com.teamoff.api.dto.request.TeamRequestDTO;
import com.teamoff.api.dto.request.TeamUserRequestDTO;
import com.teamoff.api.dto.response.UserEventsDTO;
import com.teamoff.api.infra.exception.TeamNotFoundException;
import com.teamoff.api.infra.exception.UserNotFoundException;
import com.teamoff.api.model.Team;
import com.teamoff.api.model.User;
import com.teamoff.api.repository.EventRepository;
import com.teamoff.api.repository.TeamRepository;
import com.teamoff.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventService eventService;
    private final TokenService tokenService;

    public TeamService(TeamRepository teamRepository, EventRepository eventRepository, UserRepository userRepository, EventService eventService, TokenService tokenService) {
        this.teamRepository = teamRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventService = eventService;
        this.tokenService = tokenService;
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

    public List<UserEventsDTO> getTeamEvents(UUID id, LocalDateTime startDate, LocalDateTime endDate, String token) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException("Team with id " + id + " not found"));
        String userId = tokenService.getClaim(token, "user_id");
        System.out.println("StartDate: " + startDate + " | EndDate: " + endDate);
        return eventService.groupEventsByUser(eventRepository.findAllTeamEventsBetweenDates(team, startDate, endDate), userId);
    }

    public User addUserToTeam(UUID id, TeamUserRequestDTO data) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException("Team with id " + id + " not found"));
        User user = userRepository.findById(UUID.fromString(data.userId())).orElseThrow(() -> new UserNotFoundException("User with id " + data.userId() + " not found"));

        user.getTeams().add(team);
        return user;
    }

    public User removeUserToTeam(UUID id, TeamUserRequestDTO data) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException("Team with id " + id + " not found"));
        User user = userRepository.findById(UUID.fromString(data.userId())).orElseThrow(() -> new UserNotFoundException("User with id " + data.userId() + " not found"));
        if (!user.getTeams().contains(team)) throw new UserNotFoundException("User is already not in the team");
        user.getTeams().remove(team);
        userRepository.save(user);
        return user;
    }
}
