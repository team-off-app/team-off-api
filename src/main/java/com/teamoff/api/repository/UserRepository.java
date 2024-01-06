package com.teamoff.api.repository;

import com.teamoff.api.model.Event;
import com.teamoff.api.model.Team;
import com.teamoff.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("Select u From User u " + "Where :team_id MEMBER OF u.teams ")
    List<User> findAllUsersInTeam(@Param("team_id") Team team);
}
