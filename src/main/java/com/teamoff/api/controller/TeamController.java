package com.teamoff.api.controller;

import com.teamoff.api.dto.request.TeamRequestDTO;
import com.teamoff.api.dto.response.UserEventsDTO;
import com.teamoff.api.model.Team;
import com.teamoff.api.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> create(@RequestBody @Valid TeamRequestDTO data) {
        return teamService.createTeam(data);
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
                                                  LocalDateTime endDate) {
        return teamService.getTeamEvents(id, startDate, endDate);
    }

}
