package com.teamoff.api.controller;

import com.teamoff.api.dto.UserDTO;
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

    @PostMapping
    @Transactional
    public ResponseEntity<?> create (@RequestBody @Valid UserDTO data){
        return UserService.createUser(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserByID(@PathVariable UUID id){
        return UserService.findUserById(id);
    }

    @GetMapping()
    public List<User> getAllUsers(){ return UserService.findAllUsers();
    }

}
