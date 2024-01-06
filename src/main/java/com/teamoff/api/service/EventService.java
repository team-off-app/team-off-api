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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
        User user = userRepository.findById(UUID.fromString(data.userId())).orElseThrow(() -> new UserNotFoundException("User with id " + data.userId() + " not found"));
        validateEndDate(data.startDate(),data.endDate());
        return eventRepository.save(new Event(data, user));
    }

    public ResponseEntity<?> findEventById(UUID id) {
        return new ResponseEntity<>(eventRepository.findById(id), HttpStatus.OK);
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public List<UserEventsDTO> groupEventsByUser(List<User> users, List<Event> events, String loggedUserId) {
        Map<UUID, UserEventsDTO> userEventMap = new HashMap<>();
        for (User user : users) {
            UserEventsDTO userEventsDTO = new UserEventsDTO(user);
            userEventsDTO.setEvents(new ArrayList<>());
            events.stream()
                    .filter(event -> event.getUser().equals(user))
                    .map(EventResponseDTO::new)
                    .forEach(userEventsDTO.getEvents()::add);

            userEventMap.put(user.getId(), userEventsDTO);
        }

        List<UserEventsDTO> result = new LinkedList<>(userEventMap.values());
        result.sort(Comparator.comparing((UserEventsDTO userEventsDTO) -> userEventsDTO.isLoggedUser(UUID.fromString(loggedUserId)))
                .thenComparing(UserEventsDTO::hasEvents, Comparator.reverseOrder()));

        return result;
    }


    public void deleteEventById(UUID id) {
        Event e = eventRepository.findById(id).orElseThrow(NullPointerException::new);
        eventRepository.deleteById(e.getId());
    }

    private void validateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endDate cannot be before startDate");
        }
    }
}



















