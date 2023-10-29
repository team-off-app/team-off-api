package com.teamoff.api.controller;

import com.teamoff.api.dto.create.CreateUserDTO;
import com.teamoff.api.model.User;
import com.teamoff.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create (@RequestBody @Valid CreateUserDTO data){
        User user =  userRepository.save(new User(data));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
