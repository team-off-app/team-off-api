package com.teamoff.api.controller;

import com.teamoff.api.dto.request.TeamRequestDTO;
import com.teamoff.api.dto.request.TeamUserRequestDTO;
import com.teamoff.api.dto.response.UserEventsDTO;
import com.teamoff.api.model.Team;
import com.teamoff.api.model.User;
import com.teamoff.api.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @Operation(summary = "Create a new team")
    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid TeamRequestDTO data, UriComponentsBuilder uriBuilder) {
        var team = teamService.createTeam(data);
        URI uri = uriBuilder.path("/api/team/{id}").buildAndExpand(team.getId()).toUri();
        return ResponseEntity.created(uri).body(team);
    }

    @Operation(summary = "Get a team by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamByID(@PathVariable UUID id) {
        return teamService.findTeamById(id);
    }

    @Operation(summary = "Retrieve all teams")
    @GetMapping()
    public List<Team> getAllTeams() {
        return teamService.findAllTeams();
    }

    @Operation(summary = "Get all events of a given team")
    @GetMapping("/{id}/users")
    public List<UserEventsDTO> getAllEventsByTeam(@PathVariable UUID id,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                  LocalDateTime startDate,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                  LocalDateTime endDate,
                                                  @RequestHeader(name = "Authorization") String token) {
        return teamService.getTeamEvents(id, startDate, endDate, token.replace("Bearer ",""));
    }


    @Operation(summary = "Associate a user to a given team")
    @PutMapping("/{teamId}")
    @Transactional
    public User associateTeamToUser(@PathVariable UUID teamId, @RequestBody @Valid TeamUserRequestDTO userData){
        return teamService.addUserToTeam(teamId, userData);
    }

    @Operation(summary = "Dissociate a user to a given team")
    @DeleteMapping("/{teamId}")
    public User dissociateTeamToUser(@PathVariable UUID teamId, @RequestBody @Valid TeamUserRequestDTO userData){
        return teamService.removeUserToTeam(teamId, userData);
    }



}
