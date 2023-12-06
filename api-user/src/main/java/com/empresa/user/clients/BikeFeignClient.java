package com.empresa.user.clients;

import com.empresa.user.model.Bike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "api-bike", url = "http://localhost:8003", path = "/bike")
public interface BikeFeignClient {
    @PostMapping
    Bike save(@RequestBody Bike bike);

    @GetMapping(value = "/byuser/{userId}")
    List<Bike> getBikesByUserId(@PathVariable("userId") Long userId);
}
