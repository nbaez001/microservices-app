package com.empresa.bike.controller;

import com.empresa.bike.entity.Bike;
import com.empresa.bike.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {

    @Autowired
    BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<Bike>> getAll() {
        List<Bike> bikes = bikeService.getAll();
        if (bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(bikes);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Bike> getById(@PathVariable("id") Long id) {
        Bike bike = bikeService.getBikeById(id);
        if (bike == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(bike);
        }
    }

    @PostMapping
    public ResponseEntity<Bike> save(@RequestBody Bike bike) {
        Bike bikeNew = bikeService.save(bike);
        return ResponseEntity.ok().body(bikeNew);
    }


    @GetMapping(value = "/byuser/{userId}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable("userId") Long userId) {
        List<Bike> bike = bikeService.findByUserId(userId);
        return ResponseEntity.ok().body(bike);
    }
}
