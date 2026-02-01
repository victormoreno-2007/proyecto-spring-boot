package com.construrrenta.api_gateway.infrastructure.security.filters;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.construrrenta.api_gateway.infrastructure.adapters.in.web.LoginRequest;
import com.construrrenta.api_gateway.infrastructure.adapters.out.security.JwtTokenAdapter;
import com.construrrenta.api_gateway.infrastructure.security.model.SecurityUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenAdapter jwtTokenAdapter;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenAdapter jwtTokenAdapter) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenAdapter = jwtTokenAdapter;
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return "POST".equalsIgnoreCase(request.getMethod()) && 
               "/api/v1/auth/login".equals(request.getServletPath());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, 
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );

            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException("Error al leer credenciales", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, 
                                            HttpServletResponse response, 
                                            FilterChain chain, 
                                            Authentication authResult) throws IOException {
        
        SecurityUser securityUser = (SecurityUser) authResult.getPrincipal();
        
       
        var domainUser = securityUser.getDomainUser();

        String token = jwtTokenAdapter.generateAccessToken(
            domainUser.getId(),      
            domainUser.getEmail(),  
            domainUser.getRole()   
        );

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", token);
        tokenMap.put("tokenType", "Bearer");
        
        new ObjectMapper().writeValue(response.getWriter(), tokenMap);
    }
}