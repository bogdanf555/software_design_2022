package com.foodpanda.controller;

import com.foodpanda.common.FoodDTO;
import com.foodpanda.model.Food;
import com.foodpanda.service.FoodService;
import com.foodpanda.utils.FoodCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping("/insert/{restaurantName}")
    public ResponseEntity<String> insertFood(@RequestBody Food food, @PathVariable String restaurantName) {
        String response = foodService.insertFood(food, restaurantName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(value = "/fetch/category/{restaurantName}")
    public List<FoodDTO> fetchByCategory(@RequestBody FoodCategory category, @PathVariable String restaurantName) {
        return foodService.fetchAllByCategory(restaurantName, category);
    }

    @RequestMapping(value = "/fetch/all/{restaurantName}")
    public List<FoodDTO> fetchAll(@PathVariable String restaurantName) {
        return foodService.fetchAll(restaurantName);
    }
}
