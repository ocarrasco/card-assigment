package com.example.services.impl;

import com.example.dto.UserLoginRequestDTO;
import com.example.entities.RegisteredUser;
import com.example.exceptions.UserNotFoundException;
import com.example.repositories.UserRepository;
import com.example.services.JWTService;
import com.example.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public String authenticate(UserLoginRequestDTO userLoginRequestDTO) {
        String username = userLoginRequestDTO.getUsername();
        RegisteredUser registeredUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (!passwordEncoder.matches(userLoginRequestDTO.getPassword(), registeredUser.getPassword())) {
            throw new UserNotFoundException(username);
        }

        return jwtService.generateToken(registeredUser);
    }

}
