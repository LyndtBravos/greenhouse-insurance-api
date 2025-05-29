package com.greenhouse.greenhouse_api.controller;

import com.greenhouse.greenhouse_api.model.Plan;
import com.greenhouse.greenhouse_api.model.User;
import com.greenhouse.greenhouse_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService service;

    public UserController(UserService userService) {
        service = userService;
    }

    @GetMapping("get/all")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = service.getAllUsers();
        return new ResponseEntity<>(users, users == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("get/user")
    public ResponseEntity<User> getUser(@RequestBody User user) {
        User user1 = service.findUser(user);
        return new ResponseEntity<>(user1, user1 == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("get/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        User user = service.findUserWithEmail(email);
        return new ResponseEntity<>(user, user == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @PutMapping("update/all/details")
    public ResponseEntity<String> updateUserDetails(@RequestBody User user) {
        return service.updateUser(user);
    }

    @PutMapping("update/password")
    public ResponseEntity<String> updateUserPassword(@RequestBody User user) {
        return service.updateUserPassword(user);
    }

    @DeleteMapping("delete/{userID}")
    public ResponseEntity<String> deleteUser(@PathVariable int userID){
        return service.deleteUser(userID);
    }
}