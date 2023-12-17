package com.teamoff.api.controller;

import com.teamoff.api.dto.request.UserRequestDTO;
import com.teamoff.api.dto.response.UserEventsDTO;
import com.teamoff.api.model.User;
import com.teamoff.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user and auth")
    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid UserRequestDTO data, @org.jetbrains.annotations.NotNull UriComponentsBuilder uriBuilder) {
        User user = userService.createUser(data);
        URI uri = uriBuilder.path("/api/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @Operation(summary = "Get a user by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserByID(@PathVariable UUID id) {
        return userService.findUserById(id);
    }


    @Operation(summary = "Retrieve all users")
    @GetMapping()
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @Operation(summary = "Retrieve all events given a start and end date")
    @GetMapping("/events")
    public List<UserEventsDTO> getAllEvents(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                            LocalDateTime startDate,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                            LocalDateTime endDate) {
        return userService.getUsersEvents(startDate, endDate);
    }
}
