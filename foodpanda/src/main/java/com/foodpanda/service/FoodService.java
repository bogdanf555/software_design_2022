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
import java.util.logging.Logger;


/**
 * Service that handles food operations: insertion for a restaurant, or fetching - all/by category
 */
@Service
public class FoodService {

    private static final Logger logger = Logger.getLogger(FoodService.class.getName());

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     *
     * Inserts food for a restaurant.
     *
     * @param food item to be inserted
     * @param restaurantName the name of restaurant to be inserted
     * @return response that is empty when insertions passed, or contains the error message otherwise
     */
    public String insertFood(Food food, String restaurantName) {

        String check = FoodValidator.getInstance().validateFood(food);

        if (!check.isEmpty()) {
            return check;
        }

        Restaurant restaurant = restaurantRepository.findRestaurantByName(restaurantName).orElse(null);

        if (restaurant == null)
            return "the restaurant, that you want to add food to, does not exist";

        food.setRestaurant(restaurant);
        foodRepository.save(food);

        logger.info(String.format("Food insertion done for: %s, %s", food.getName(), restaurantName));

        return "";
    }

    /**
     *
     * Fetches all the foods from a restaurant that belong to a certain category.
     *
     * @param restaurantName for the restaurant to get information from
     * @param category LUNCH/BREAKFAST/DINNER to filter the foods
     * @return a list of the fetched food information
     */
    public List<FoodDTO> fetchAllByCategory(String restaurantName, FoodCategory category) {

        Restaurant restaurant = restaurantRepository.findRestaurantByName(restaurantName).orElse(null);

        if (restaurant == null)
            return null;

        List<Food> foods = foodRepository.findFoodsByRestaurantAndCategory(restaurant, category).orElse(null);

        logger.info(String.format("Fetch all by category done for: %s, %s", restaurant.getName(), category.toString()));

        return FoodMapper.getInstance().convertListToDTO(foods);
    }

    /**
     *
     * Fetches all the foods available for a restaurant
     *
     * @param restaurantName to fetch all the foods from
     * @return a list of the fetched food information
     */
    public List<FoodDTO> fetchAll(String restaurantName) {

        Restaurant restaurant = restaurantRepository.findRestaurantByName(restaurantName).orElse(null);

        if (restaurant == null)
            return null;

        List<Food> foods = foodRepository.findFoodsByRestaurant(restaurant).orElse(null);

        logger.info(String.format("Fetch all done for: %s", restaurant.getName()));

        return FoodMapper.getInstance().convertListToDTO(foods);
    }
}
