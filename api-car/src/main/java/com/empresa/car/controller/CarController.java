package com.empresa.car.controller;

import com.empresa.car.entity.Car;
import com.empresa.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAll() {
        List<Car> cars = carService.getAll();
        if (cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(cars);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Car> getById(@PathVariable("id") Long id) {
        Car car = carService.getCarById(id);
        if (car == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(car);
        }
    }

    @PostMapping
    public ResponseEntity<Car> save(@RequestBody Car car) {
        Car carNew = carService.save(car);
        return ResponseEntity.ok().body(carNew);
    }


    @GetMapping(value = "/byuser/{userId}")
    public ResponseEntity<List<Car>> getByUserId(@PathVariable("userId") Long userId) {
        List<Car> car = carService.findByUserId(userId);
        return ResponseEntity.ok().body(car);
    }
}
