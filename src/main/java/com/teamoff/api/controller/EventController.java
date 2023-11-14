package com.teamoff.api.controller;

import com.teamoff.api.dto.request.EventRequestDTO;
import com.teamoff.api.model.Event;
import com.teamoff.api.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> create(@RequestBody @Valid EventRequestDTO data) {
        return eventService.createEvent(data);
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