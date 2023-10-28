package com.teamoff.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity(name = "Team")
@Table(name = "team")
@Data
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true ,nullable = false)
    private String name;

    @ManyToMany
    private Set<User> user;





}
