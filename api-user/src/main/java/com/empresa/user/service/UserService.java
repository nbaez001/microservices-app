package com.empresa.user.service;

import com.empresa.user.entity.User;
import com.empresa.user.model.Bike;
import com.empresa.user.model.Car;
import com.empresa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RestTemplate restTemplate;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<Car> getCars(Long id) {
        List<Car> cars = restTemplate.getForObject("http://localhost:8002/car/byuser/" + id, List.class);
        return cars;
    }

    public List<Bike> getBikes(Long id) {
        List<Bike> bikes = restTemplate.getForObject("http://localhost:8003/bike/byuser/" + id, List.class);
        return bikes;
    }
}
