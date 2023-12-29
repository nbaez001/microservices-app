package com.empresa.user.controller;

import com.empresa.user.entity.User;
import com.empresa.user.model.Bike;
import com.empresa.user.model.Car;
import com.empresa.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @CircuitBreaker(name = "carscb", fallbackMethod = "fallbackGetCars")
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

    @CircuitBreaker(name = "carscb", fallbackMethod = "fallbackSaveCar")
    @PostMapping(value = "/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") Long userId, @RequestBody Car car) {
        if (userService.getUserById(userId) == null)
            return ResponseEntity.notFound().build();
        Car carNew = userService.saveCar(userId, car);
        return ResponseEntity.ok().body(carNew);
    }

    @CircuitBreaker(name = "bikescb", fallbackMethod = "fallbackGetBikes")
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

    @CircuitBreaker(name = "bikescb", fallbackMethod = "fallbackSaveBike")
    @PostMapping(value = "/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") Long userId, @RequestBody Bike bike) {
        if (userService.getUserById(userId) == null)
            return ResponseEntity.notFound().build();
        Bike bikeNew = userService.saveBike(userId, bike);
        return ResponseEntity.ok().body(bikeNew);
    }

    @CircuitBreaker(name = "allcb", fallbackMethod = "fallbackGetAll")
    @GetMapping(value = "/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable("userId") Long userId) {
        Map<String, Object> map = userService.getUserAndVehicles(userId);
        return ResponseEntity.ok().body(map);
    }


    private ResponseEntity<List<Car>> fallbackGetCars(@PathVariable("userId") Long userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene los coches en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallbackSaveCar(@PathVariable("userId") Long userId, @RequestBody Car car, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " no tiene dinero para coches", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallbackGetBikes(@PathVariable("userId") Long userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallbackSaveBike(@PathVariable("userId") Long userId, @RequestBody Bike bike, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " no tiene dinero para motos", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallbackGetAll(@PathVariable("userId") Long userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene los vehiculos en el taller", HttpStatus.OK);
    }
}
