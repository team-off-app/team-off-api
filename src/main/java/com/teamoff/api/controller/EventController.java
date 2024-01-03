package com.teamoff.api.controller;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.teamoff.api.dto.request.EventRequestDTO;
import com.teamoff.api.model.Event;
import com.teamoff.api.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Create a new event")
    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid EventRequestDTO data, UriComponentsBuilder uriBuilder) {
        Event event = eventService.createEvent(data);
        URI uri = uriBuilder.path("/api/event/{id}").buildAndExpand(event.getId()).toUri();
        return ResponseEntity.created(uri).body(event);
    }

    @Operation(summary = "Get a event by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable UUID id) {
        return eventService.findEventById(id);
    }

    @Operation(summary = "Retrieve all events")
    @GetMapping()
    public List<Event> getAllUsers() {
        return eventService.findAllEvents();
    }

    @Operation(summary = "Delete a event by its ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById (@PathVariable UUID id ) {
       eventService.deleteEventById(id);
    }
}