package com.greenhouse.greenhouse_api.service;

import com.greenhouse.greenhouse_api.model.User;
import com.greenhouse.greenhouse_api.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User findUserWithEmail(String email) {
        return userRepo.findUserWithEmail(email);
    }

    public User findUser(User user) {
        return userRepo.findUser(user);
    }

    public ResponseEntity<String> createUser(User user) {
        return userRepo.insert(user);
    }

    public ResponseEntity<String> updateUser(User user) {
        return userRepo.updateAll(user);
    }

    public ResponseEntity<String> updateUserPassword(User user) {
        return userRepo.updatePassword(user.getEmail(), user.getPassword());
    }

    public ResponseEntity<String> deleteUser(int userID) {
        return userRepo.delete(userID);
    }
}