package com.example.controllers.impl;

import com.example.controllers.UserController;
import com.example.dto.JwtResponseDTO;
import com.example.dto.UserLoginRequestDTO;
import com.example.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public JwtResponseDTO authenticate(UserLoginRequestDTO userLoginRequestDTO) {
        log.info("Authenticating user {}", userLoginRequestDTO.getUsername());
        String token = userService.authenticate(userLoginRequestDTO);
        return new JwtResponseDTO(token);
    }

}
