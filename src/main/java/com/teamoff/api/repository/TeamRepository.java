package com.teamoff.api.repository;

import com.teamoff.api.model.Auth;
import com.teamoff.api.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    @Query("Select t From Team t Where t.id = :uuid")
    Team findTeamById(UUID uuid);
}
