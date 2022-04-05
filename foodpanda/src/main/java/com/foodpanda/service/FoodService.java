package com.foodpanda.service;

import com.foodpanda.common.FoodDTO;
import com.foodpanda.mappers.FoodMapper;
import com.foodpanda.model.Food;
import com.foodpanda.model.Restaurant;
import com.foodpanda.repository.FoodRepository;
import com.foodpanda.repository.RestaurantRepository;
import com.foodpanda.utils.FoodCategory;
import com.foodpanda.validators.FoodValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public String insertFood(Food food, String restaurantName) {

        String check = FoodValidator.getInstance().validateFood(food);

        if (!check.isEmpty())
            return check;

        Restaurant restaurant = restaurantRepository.findRestaurantByName(restaurantName).orElse(null);

        if (restaurant == null)
            return "the restaurant, that you want to add food to, does not exist";

        food.setRestaurant(restaurant);
        foodRepository.save(food);

        return "the food " + food.getName() + " was added successfully to restaurant " + restaurantName;
    }

    public List<FoodDTO> fetchAllByCategory(String restaurantName, FoodCategory category) {

        Restaurant restaurant = restaurantRepository.findRestaurantByName(restaurantName).orElse(null);

        if (restaurant == null)
            return null;

        List<Food> foods = foodRepository.findFoodsByRestaurantAndCategory(restaurant, category).orElse(null);

        return FoodMapper.getInstance().convertListToDTO(foods);
    }

    public List<FoodDTO> fetchAll(String restaurantName) {

        Restaurant restaurant = restaurantRepository.findRestaurantByName(restaurantName).orElse(null);

        if (restaurant == null)
            return null;

        List<Food> foods = foodRepository.findFoodsByRestaurant(restaurant).orElse(null);

        return FoodMapper.getInstance().convertListToDTO(foods);
    }
}
