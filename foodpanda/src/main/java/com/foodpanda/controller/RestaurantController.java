package com.foodpanda.controller;

import com.foodpanda.common.RestaurantDTO;
import com.foodpanda.model.Restaurant;
import com.foodpanda.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(value = "/fetch/all")
    public List<RestaurantDTO> fetchAll() {
        return restaurantService.fetchAll();}

    @PostMapping("/insert/{adminName}")
    public ResponseEntity<String> insertRestaurant(@PathVariable String adminName, @RequestBody Restaurant restaurant) {

        String response = restaurantService.insertRestaurant(restaurant, adminName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}