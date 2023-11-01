package com.teamoff.api.service;

import com.teamoff.api.dto.request.UserRequestDTO;
import com.teamoff.api.model.User;
import com.teamoff.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
