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

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

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

    public String insertUser(User user) {

        String response = UserValidator.getInstance().validateUser(user);

        try {
            user.setPassword(this.encryptPassword(user.getPassword()));
        }
        catch(NoSuchAlgorithmException e) {
            return "couldn't encrypt password";
        }

        userRepository.save(user);

        return response;
    }

    public UserDTO login(LoginDTO loginDTO) {

        try {
            loginDTO.setPassword(this.encryptPassword(loginDTO.getPassword()));
        }
        catch(NoSuchAlgorithmException e) {
            return null;
        }

        User user = userRepository.findUserByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword()).orElse(null);

        return UserMapper.getInstance().convertToDTO(user);
    }
}
