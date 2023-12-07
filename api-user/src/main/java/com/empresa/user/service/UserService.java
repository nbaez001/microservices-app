package com.empresa.user.service;

import com.empresa.user.clients.BikeFeignClient;
import com.empresa.user.clients.CarFeignClient;
import com.empresa.user.entity.User;
import com.empresa.user.model.Bike;
import com.empresa.user.model.Car;
import com.empresa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CarFeignClient carFeignClient;

    @Autowired
    BikeFeignClient bikeFeignClient;

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
        List<Car> cars = restTemplate.getForObject("http://api-car/car/byuser/" + id, List.class);
        return cars;
    }

    public List<Bike> getBikes(Long id) {
        List<Bike> bikes = restTemplate.getForObject("http://api-bike/bike/byuser/" + id, List.class);
        return bikes;
    }

    public Car saveCar(Long userId, Car car) {
        car.setUserId(userId);
        return carFeignClient.save(car);
    }

    public Bike saveBike(Long userId, Bike bike) {
        bike.setUserId(userId);
        return bikeFeignClient.save(bike);
    }

    public Map<String, Object> getUserAndVehicles(Long userId){
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            result.put("Mensaje", "no existe usuario");
            return result;
        }
        result.put("User",user);

        List<Car> cars = carFeignClient.getCarsByUserId(userId);
        if(cars.isEmpty())
            result.put("Cars","Ese user no tiene coches");
        else
            result.put("Cars",cars);

        List<Bike> bikes = bikeFeignClient.getBikesByUserId(userId);
        if(bikes.isEmpty())
            result.put("Bikes","Ese user no tiene motos");
        else
            result.put("Bikes",bikes);
        return result;
    }
}
