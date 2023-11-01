package com.teamoff.api.controller;

import com.teamoff.api.dto.TeamDTO;
import com.teamoff.api.model.Team;
import com.teamoff.api.repository.TeamRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create (@RequestBody @Valid TeamDTO data){
        Team user =  teamRepository.save(new Team(data));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Team getTeamByID(@PathVariable UUID id){
        return teamRepository.findById(id).orElse(null);
    }

    @GetMapping()
    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

}
