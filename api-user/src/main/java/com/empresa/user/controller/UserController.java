package com.empresa.user.controller;

import com.empresa.user.entity.User;
import com.empresa.user.model.Bike;
import com.empresa.user.model.Car;
import com.empresa.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(users);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(user);
        }
    }

    @PostMapping
    public ResponseEntity<User> getById(@RequestBody User user) {
        User userNew = userService.save(user);
        return ResponseEntity.ok().body(userNew);
    }

    @GetMapping(value = "/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<Car> cars = userService.getCars(userId);
            return ResponseEntity.ok().body(cars);
        }
    }

    @GetMapping(value = "/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<Bike> bikes = userService.getBikes(userId);
            return ResponseEntity.ok().body(bikes);
        }
    }
}
