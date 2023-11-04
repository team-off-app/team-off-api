package com.teamoff.api.repository;

import com.teamoff.api.model.Event;
import com.teamoff.api.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    @Query("SELECT e FROM Event e WHERE e.startDate BETWEEN :startDate AND :endDate")
    List<Event> findAllEventsBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT e FROM Event e JOIN e.user u WHERE :team_id MEMBER OF u.teams AND e.startDate BETWEEN :startDate AND :endDate")
    List<Event> findAllTeamEventsBetweenDates(@Param("team_id") Team team, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
