package com.teamoff.api.repository;

import com.teamoff.api.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface AuthRepository extends JpaRepository<Auth, UUID> {
    UserDetails findByLogin(String login);

    @Query("Select a From Auth a Where a.id = :uuid")
    Auth findAuthById(UUID uuid);
}
