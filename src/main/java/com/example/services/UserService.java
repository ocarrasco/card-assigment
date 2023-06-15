package com.example.services;

import com.example.dto.UserLoginRequestDTO;

public interface UserService {

    String authenticate(UserLoginRequestDTO userLoginRequestDTO);

}
