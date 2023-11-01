package com.teamoff.api.controller;

import com.teamoff.api.dto.request.UserRequestDTO;
import com.teamoff.api.model.User;
import com.teamoff.api.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create (@RequestBody @Valid UserRequestDTO data){
        return userService.createUser(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserByID(@PathVariable UUID id){
        return userService.findUserById(id);
    }

    @GetMapping()
    public List<User> getAllUsers(){ return userService.findAllUsers();
    }

}
