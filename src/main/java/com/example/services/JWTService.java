package com.example.services;

import com.example.entities.RegisteredUser;

public interface JWTService {

    /**
     * Create a JWT Token for user.
     *
     * @param registeredUser
     * @return generated token.
     */
    String generateToken(RegisteredUser registeredUser);

    /**
     * Check if token is valid.
     *
     * @param token
     * @param registeredUser
     * @return
     */
    boolean validateJwtToken(String token, RegisteredUser registeredUser);

    /**
     * Decode token retrieving the user.
     *
     * @param token
     * @return
     */
    String getUsernameFromToken(String token);

}
