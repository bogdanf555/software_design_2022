package com.foodpanda.controller;

import com.foodpanda.common.FoodDTO;
import com.foodpanda.common.RestaurantDTO;
import com.foodpanda.common.UserDTO;
import com.foodpanda.model.Restaurant;
import com.foodpanda.service.FoodService;
import com.foodpanda.service.RestaurantService;
import com.foodpanda.utils.JwtHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value="/restaurant")
public class RestaurantController{

    private static final Logger logger = Logger.getLogger(RestaurantController.class.getName());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private FoodService foodService;

    @RequestMapping(value = "/fetch/all")
    public List<RestaurantDTO> fetchAll(@RequestHeader("token") String token) {

        try {
            JwtHandler.decodeJWT(token);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        logger.info("Received request for: Fetching all available restaurants");
        return restaurantService.fetchAll();
    }

    @RequestMapping(value = "/fetch/")
    public RestaurantDTO fetchRestaurant(@RequestHeader("token") String token) {

        UserDTO admin;
        try {
            admin = JwtHandler.decodeJWT(token);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        RestaurantDTO restaurantDTO = restaurantService.fetchRestaurant(admin.getUsername());

        if (restaurantDTO != null)
            logger.info(String.format("Fetched restaurants for admin %s", admin.getUsername()));
        else
            logger.warning(String.format("Could not fetch restaurants for admin %s", admin.getUsername()));

        return restaurantDTO;
    }

    @PostMapping("/insert/")
    public ResponseEntity<String> insertRestaurant(@RequestHeader("token") String token,
                                                   @RequestBody Restaurant restaurant) {

        UserDTO admin;
        try {
            admin = JwtHandler.decodeJWT(token);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        String response = restaurantService.insertRestaurant(restaurant, admin.getUsername());

        if (response.isEmpty())
            logger.info(String.format(
                    "Restaurant %s was successfully inserted for admin %s",
                    restaurant.getName(),
                    admin.getUsername()
                )
            );
        else
            logger.warning(String.format(
                    "Restaurant %s failed to be inserted for admin %s with error: %s",
                    restaurant.getName(),
                    admin.getUsername(),
                    response
                )
            );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(value = "/pdf/", produces = "application/pdf")
    public ResponseEntity<byte[]> exportFoodsAsPDF(@RequestHeader("token") String token) {

        UserDTO admin;
        try {
            admin = JwtHandler.decodeJWT(token);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        RestaurantDTO restaurant = restaurantService.fetchRestaurant(admin.getUsername());
        List<FoodDTO> foods = foodService.fetchAll(restaurant.getName());

        String filename = "pdf_menus/" + restaurant.getName() + ".pdf";
        byte[] contents = restaurantService.exportPDF(restaurant, foods, filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);

        return response;
    }
}