package com.teamoff.api.controller;

import com.teamoff.api.dto.request.TeamRequestDTO;
import com.teamoff.api.model.Team;
import com.teamoff.api.service.TeamService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @PostMapping
    @Transactional
    public ResponseEntity<?> create (@RequestBody @Valid TeamRequestDTO data){
        return TeamService.createTeam(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamByID(@PathVariable UUID id){
        return TeamService.findTeamById(id);
    }

    @GetMapping()
    public List<Team> getAllTeams(){
        return TeamService.findAllTeams();
    }

}
