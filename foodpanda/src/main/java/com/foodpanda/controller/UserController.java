package com.foodpanda.controller;

import com.foodpanda.common.LoginDTO;
import com.foodpanda.common.UserDTO;
import com.foodpanda.mappers.UserMapper;
import com.foodpanda.model.User;
import com.foodpanda.service.UserService;
import com.foodpanda.utils.JwtHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping(value="/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {

        String response = userService.insertUser(user);
        UserDTO userDTO = UserMapper.getInstance().convertToDTO(user);

        if (response.isEmpty()) {
            logger.info(String.format("User %s was successfully registered", user.getUsername()));
        }
        else {
            logger.warning(String.format(
                    "User %s failed to register. Issue: %s", user.getUsername(), response)
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(JwtHandler.createJWT(userDTO, response));
    }

    @RequestMapping(value = "/login", produces = "application/json")
    public String login(@RequestBody LoginDTO loginDTO) {

        UserDTO userDTO = userService.login(loginDTO);

        if (userDTO != null)
            logger.info(String.format("User %s has logged in successfully", userDTO.getUsername()));
        else {
            logger.warning(String.format("User %s failed to login.", loginDTO.getUsername()));
            return "";
        }

        return JwtHandler.createJWT(userDTO, null);
    }
}
