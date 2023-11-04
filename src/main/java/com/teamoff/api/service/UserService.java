package com.teamoff.api.service;

import com.teamoff.api.dto.request.UserRequestDTO;
import com.teamoff.api.dto.response.EventResponseDTO;
import com.teamoff.api.dto.response.UserEventsDTO;
import com.teamoff.api.model.Event;
import com.teamoff.api.model.User;
import com.teamoff.api.repository.EventRepository;
import com.teamoff.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class UserService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public UserService(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public ResponseEntity<Object> findUserById(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> createUser(UserRequestDTO data) {
        return new ResponseEntity<>(
                userRepository.save(new User(data)),
                HttpStatus.CREATED
        );
    }

    public List<UserEventsDTO> getUsersEvents(LocalDateTime startDate, LocalDateTime endDate) {
        List<Event> events = eventRepository.findAllEventsBetweenDates(startDate, endDate);
        Map<UUID, UserEventsDTO> userEventMap = new HashMap<>();

        events.forEach(event -> {
                    User user = event.getUser();
                    UserEventsDTO userEventsDTO = userEventMap.get(user.getId());
                    if (userEventsDTO == null) {
                        userEventsDTO = new UserEventsDTO(user);
                        userEventsDTO.setEvents(new ArrayList<>());
                        userEventMap.put(user.getId(), userEventsDTO);
                    }
                    EventResponseDTO eventResponseDTO = new EventResponseDTO(event);
                    userEventsDTO.getEvents().add(eventResponseDTO);
                }
        );

        return new ArrayList<>(userEventMap.values());
    }
}
