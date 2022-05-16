package com.foodpanda.service;

import com.foodpanda.common.LoginDTO;
import com.foodpanda.common.UserDTO;
import com.foodpanda.mappers.UserMapper;
import com.foodpanda.model.User;
import com.foodpanda.repository.UserRepository;
import com.foodpanda.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Service that handles operations on users: register, login
 */
@Service
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserRepository userRepository;

    /**
     * Algorithm that hashes the password of the user using SHA-256
     *
     * @param password the user's credential to be hashed
     * @return the hashed password
     * @throws NoSuchAlgorithmException if somehow the SHA-256 algorithm is not found
     */
    public String encryptPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] passwordBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        BigInteger number = new BigInteger(1, passwordBytes);

        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    /**
     *
     * Registeres a new user in the application.
     *
     * @param user the new user to be registered
     * @return a response string which is empty on successful registration, otherwise contains
     *          the error message
     */
    public String insertUser(User user) {

        String response = UserValidator.getInstance().validateUser(user);

        try {
            user.setPassword(this.encryptPassword(user.getPassword()));
            logger.info(String.format(
                    "Encrypt password while registering in for user %s",
                    user.getUsername()
            ));
        }
        catch(NoSuchAlgorithmException e) {
            logger.severe(String.format(
                    "Failed to encrypt password while registering for user %s",
                    user.getUsername()
            ));
            return "couldn't encrypt password";
        }

        userRepository.save(user);

        return response;
    }

    /**
     *
     * Performs login for a provided user's credentials
     *
     * @param loginDTO credentials passed for login
     * @return on successful login, information about the user is returned, otherwise null
     */
    public UserDTO login(LoginDTO loginDTO) {

        try {
            loginDTO.setPassword(this.encryptPassword(loginDTO.getPassword()));
            logger.info(String.format(
                    "Encrypt password while logging in for user %s",
                    loginDTO.getUsername()
            ));
        }
        catch(NoSuchAlgorithmException e) {
            logger.severe(String.format("Failed to encrypt password while loggin in for user %s", loginDTO.getUsername()));
            return null;
        }

        User user = userRepository.findUserByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword()).orElse(null);

        return UserMapper.getInstance().convertToDTO(user);
    }
}
