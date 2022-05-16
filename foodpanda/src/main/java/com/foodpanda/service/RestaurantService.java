package com.foodpanda.service;

import com.foodpanda.common.FoodDTO;
import com.foodpanda.common.RestaurantDTO;
import com.foodpanda.mappers.RestaurantMapper;
import com.foodpanda.model.Restaurant;
import com.foodpanda.model.User;
import com.foodpanda.repository.RestaurantRepository;
import com.foodpanda.repository.UserRepository;
import com.foodpanda.utils.PdfGenerator;
import com.foodpanda.validators.UserValidator;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service that handles operations on restaurants: validation, insertions and information fetching
 */
@Service
public class RestaurantService {

    private static final Logger logger = Logger.getLogger(RestaurantService.class.getName());

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PdfGenerator pdfGenerator;

    /**
     * Fetches all the available restaurants registered in the application.
     *
     * @return a list of restaurant information
     */
    public List<RestaurantDTO> fetchAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        List<RestaurantDTO> restaurantDTOs = new ArrayList<>();

        for (Restaurant restaurant: restaurants) {
            restaurantDTOs.add(RestaurantMapper.getInstance().convertToDTO(restaurant));
        }

        logger.info("Fetched all restaurants");

        return restaurantDTOs;
    }

    /**
     *
     * Registers a new restaurant in the application and assigns it to an existing
     * user as admin.
     *
     * @param restaurant the object to be registered in the application
     * @param adminName the name of the admin for the restaurant to be assigned to
     * @return a response string that is empty on successful insertion, or contains the
     *          error message otherwise
     */
    public String insertRestaurant(Restaurant restaurant, String adminName) {

        User admin = userRepository.findUserByUsername(adminName).orElse(null);

        if (admin == null) {
            logger.warning(String.format("The name provided %s is not the admin for restaurant %s",
                            adminName,
                            restaurant.getName())
                    );
            return "The user " + adminName + " is not in database";
        }

        String response = UserValidator.getInstance().validateAdmin(admin);

        if (!response.isEmpty()) {

            logger.warning(String.format("The user is not valid %s",
                    adminName)
            );

            return response;
        }

        restaurant.setAdmin(admin);

        restaurantRepository.save(restaurant);

        return "";
    }

    /**
     *
     * Fetches a restaurant's information based on its unique admin name.
     *
     * @param adminName the administrator of the restaurant
     * @return object containing the restaurants information
     */
    public RestaurantDTO fetchRestaurant(String adminName) {

        User admin = userRepository.findUserByUsername(adminName).orElse(null);

        if (admin == null) {
            logger.warning(String.format("The name provided %s is not an a user",
                    adminName)
            );
            return null;
        }

        String response = UserValidator.getInstance().validateAdmin(admin);

        if (!response.isEmpty()) {

            logger.warning(String.format("The user provided %s is not an an admin",
                    adminName)
            );

            return null;
        }

        Restaurant restaurant = restaurantRepository.findRestaurantByAdmin(admin).orElse(null);

        if (restaurant == null) {

            logger.warning(String.format("The user provided %s has no restaurant registered",
                    adminName)
            );
            return null;
        }

        return RestaurantMapper.getInstance().convertToDTO(restaurant);
    }

    /**
     *
     * Creates locally a pdf file containing the menu of a restaurant.
     *
     * @param restaurant entity to generate pdf menu for
     * @param foods the list of foods in the menu
     * @param filename the file creation location on the server
     * @return raw bytes of containing the pdf file
     */
    public byte[] exportPDF(RestaurantDTO restaurant, List<FoodDTO> foods, String filename) {

        try {
            pdfGenerator.generatePDF(restaurant, foods, "pdf_menus/");
        } catch (FileNotFoundException | DocumentException e){
            e.printStackTrace();
        }

        File file = new File(filename);
        byte[] contents;

        try {
            contents = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            contents = null;
        }

        return contents;
    }
}
