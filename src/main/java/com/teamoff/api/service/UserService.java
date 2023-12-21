package com.teamoff.api.service;

import com.teamoff.api.dto.request.UserAuthRequestDTO;
import com.teamoff.api.dto.response.UserEventsDTO;
import com.teamoff.api.model.User;
import com.teamoff.api.repository.EventRepository;
import com.teamoff.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class UserService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventService eventService;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, EventRepository eventRepository, EventService eventService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.eventService = eventService;
        this.tokenService = tokenService;
    }

    public ResponseEntity<Object> findUserById(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserAuthRequestDTO data) {
        return userRepository.save(new User(data));
    }

    public List<UserEventsDTO> getUsersEvents(LocalDateTime startDate, LocalDateTime endDate, String token) {
        String userId = tokenService.getClaim(token, "user_id");
        return eventService.groupEventsByUser(eventRepository.findAllEventsBetweenDates(startDate, endDate), userId);

    }
}
