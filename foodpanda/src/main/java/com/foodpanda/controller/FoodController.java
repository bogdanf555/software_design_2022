package com.foodpanda.controller;

import com.foodpanda.common.FoodDTO;
import com.foodpanda.common.UserDTO;
import com.foodpanda.model.Food;
import com.foodpanda.service.FoodService;
import com.foodpanda.utils.FoodCategory;
import com.foodpanda.utils.JwtHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value="/food")
public class FoodController {

    private static final Logger logger = Logger.getLogger(FoodController.class.getName());

    @Autowired
    private FoodService foodService;

    @PostMapping("/insert/{restaurantName}")
    public ResponseEntity<String> insertFood(@RequestHeader("token") String token,
                                             @RequestBody Food food,
                                             @PathVariable String restaurantName) {
        UserDTO admin;
        try {
            admin = JwtHandler.decodeJWT(token);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("please authenticate");
        }

        String response = foodService.insertFood(food, restaurantName);
        if (response.isEmpty())
            logger.info(String.format(
                            "Food %s was successfully inserted for restaurant %s",
                            food.getName(),
                            restaurantName
                    )
            );
        else
            logger.warning(String.format(
                            "Food %s failed to be inserted for restaurant %s with error: %s",
                            food.getName(),
                            restaurantName,
                            response
                    )
            );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(value = "/fetch/category/{restaurantName}")
    public List<FoodDTO> fetchByCategory(@RequestHeader("token") String token,
                                         @RequestBody FoodCategory category,
                                         @PathVariable String restaurantName) {

        UserDTO admin;
        try {
            admin = JwtHandler.decodeJWT(token);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        List<FoodDTO> foods = foodService.fetchAllByCategory(restaurantName, category);

        if (foods != null && !foods.isEmpty())
            logger.info(String.format(
                    "Successfully fetched foods for category %s from restaurant %s",
                    category,
                    restaurantName
            ));
        else
            logger.warning(String.format(
                    "Failed to fetch foods for category %s from restaurant %s",
                    category,
                    restaurantName
            ));

        return foods;
    }

    @RequestMapping(value = "/fetch/all/{restaurantName}")
    public List<FoodDTO> fetchAll(@RequestHeader("token") String token,
                                  @PathVariable String restaurantName) {

        UserDTO admin;
        try {
            admin = JwtHandler.decodeJWT(token);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        logger.info(String.format(
                "Received request for: Fetching all available foods for restaurant %s",
                restaurantName)
        );

        return foodService.fetchAll(restaurantName);
    }
}
