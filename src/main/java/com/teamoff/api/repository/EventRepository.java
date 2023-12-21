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
    @Query("SELECT e FROM Event e " +
            "WHERE e.startDate <= :startDate " +
            "AND e.endDate >= :endDate")
    List<Event> findAllEventsBetweenDates(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    @Query("Select e From Event e " +
            "Join User u on e.user.id = u.id " +
            "Where :team_id MEMBER OF u.teams " +
            "And e.startDate BETWEEN :startDate AND :endDate " +
            "And e.endDate BETWEEN :startDate AND :endDate " +
            "Order By e.startDate Asc")
    List<Event> findAllTeamEventsBetweenDates(@Param("team_id") Team team,
                                              @Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);
}
