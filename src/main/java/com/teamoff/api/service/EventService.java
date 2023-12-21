package com.teamoff.api.service;

import com.teamoff.api.dto.request.EventRequestDTO;
import com.teamoff.api.dto.response.EventResponseDTO;
import com.teamoff.api.dto.response.UserEventsDTO;
import com.teamoff.api.infra.exception.UserNotFoundException;
import com.teamoff.api.model.Event;
import com.teamoff.api.model.User;
import com.teamoff.api.repository.EventRepository;
import com.teamoff.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {


    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Event createEvent(EventRequestDTO data) {
        User user = userRepository.findById(UUID.fromString(data.userId())).orElse(null);
        if (user == null) throw new UserNotFoundException("User not found exception");
        return eventRepository.save(new Event(data, user));
    }

    public ResponseEntity<?> findEventById(UUID id) {
        return new ResponseEntity<>(eventRepository.findById(id), HttpStatus.OK);
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public List<UserEventsDTO> groupEventsByUser(List<Event> events, String userId) {
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

        List<UserEventsDTO> result = new ArrayList<>(userEventMap.values());
        if (!userId.isEmpty()) {
            result.sort(Comparator.comparingInt(u -> u.getId().toString().equals(userId) ? -1 : 0));
        }

        return result;
    }


    public void deleteEventById(UUID id) {
        Event e = eventRepository.findById(id).orElseThrow(NullPointerException::new);
        eventRepository.deleteById(e.getId());
    }
}



















