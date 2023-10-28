package com.teamoff.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity(name = "User")
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique=true, nullable = false)
    private String username;
    private String photoUrl;

    @ManyToMany
    @JoinTable(name = "team_user",
    joinColumns = @JoinColumn(name = "team_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<Team> team;
    }

