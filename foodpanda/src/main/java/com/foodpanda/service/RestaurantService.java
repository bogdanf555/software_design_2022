package com.foodpanda.service;

import com.foodpanda.common.RestaurantDTO;
import com.foodpanda.mappers.RestaurantMapper;
import com.foodpanda.model.Restaurant;
import com.foodpanda.model.User;
import com.foodpanda.repository.RestaurantRepository;
import com.foodpanda.repository.UserRepository;
import com.foodpanda.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    public List<RestaurantDTO> fetchAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        List<RestaurantDTO> restaurantDTOs = new ArrayList<>();

        for (Restaurant restaurant: restaurants) {
            restaurantDTOs.add(RestaurantMapper.getInstance().convertToDTO(restaurant));
        }

        return restaurantDTOs;
    }

    public String insertRestaurant(Restaurant restaurant, String adminName) {

        User admin = userRepository.findUserByUsername(adminName).orElse(null);

        if (admin == null)
            return "The user " + adminName + " is not in database";

        String response = UserValidator.getInstance().validateAdmin(admin);

        if (!response.isEmpty())
            return response;

        restaurant.setAdmin(admin);

        restaurantRepository.save(restaurant);

        return "";
    }
}
