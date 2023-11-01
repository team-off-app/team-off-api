package com.teamoff.api.controller;

import com.teamoff.api.dto.request.EventRequestDTO;
import com.teamoff.api.model.Event;
import com.teamoff.api.service.EventService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    @PostMapping
    @Transactional
    public ResponseEntity<?> create (@RequestBody @Valid EventRequestDTO data){
        return eventService.createEvent(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable UUID id){
        return eventService.findEventById(id);
    }

    @GetMapping()
    public List<Event> getAllUsers(){ return eventService.findAllEvents();
    }

}
