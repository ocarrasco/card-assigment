package com.example.config;

import com.example.filters.JWTAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PATH = "/users/auth";

    private static final String[] EXCLUDED_GET_PATHS = new String[]{
            "/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
                .and().ignoring().antMatchers(HttpMethod.POST, LOGIN_PATH)
                .and().ignoring().antMatchers(HttpMethod.GET, EXCLUDED_GET_PATHS);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(authorize -> authorize.anyRequest().authenticated())
                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }

}
