package com.example.filters;

import com.example.dto.LoggedUserDTO;
import com.example.exceptions.UserNotFoundException;
import com.example.repositories.UserRepository;
import com.example.services.JWTService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private static final String BEARER_HEADER = "Bearer ";

    private final JWTService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER_HEADER)) {
            checkToken(requestTokenHeader.substring(BEARER_HEADER.length()));
        }

        filterChain.doFilter(request, response);
    }

    private void checkToken(String token) {
        String loggedUserEmail = jwtService.getUsernameFromToken(token);

        var registeredUser = userRepository.findByEmail(loggedUserEmail)
                .orElseThrow(() -> new UserNotFoundException(loggedUserEmail));

        if (!jwtService.validateJwtToken(token, registeredUser)) {
            throw new UserNotFoundException(loggedUserEmail);
        }

        var loggedUser = LoggedUserDTO.builder()
                .id(registeredUser.getId())
                .email(registeredUser.getEmail())
                .role(registeredUser.getRole())
                .build();

        var auth = new UsernamePasswordAuthenticationToken(loggedUser, null, List.of(
                new SimpleGrantedAuthority(registeredUser.getRole().getText())
        ));

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}
