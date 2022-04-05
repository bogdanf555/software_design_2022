package com.foodpanda.mappers;

import com.foodpanda.common.RestaurantDTO;
import com.foodpanda.model.Restaurant;

public class RestaurantMapper {

    private static RestaurantMapper restaurantMapper;

    private RestaurantMapper() {

    }

    public static RestaurantMapper getInstance() {

        if (restaurantMapper == null) {
            restaurantMapper = new RestaurantMapper();
        }

        return restaurantMapper;
    }

    public Restaurant convertToRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(restaurantDTO.getName());
        restaurant.setLocation(restaurantDTO.getLocation());

        return restaurant;
    }

    public RestaurantDTO convertToDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();

        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setLocation(restaurant.getLocation());

        return restaurantDTO;
    }
}
