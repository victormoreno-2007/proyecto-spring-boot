package com.construrrenta.api_gateway.infrastructure.security.config;

import com.construrrenta.api_gateway.infrastructure.adapters.out.security.JwtTokenAdapter;
import com.construrrenta.api_gateway.infrastructure.security.filters.JwtAuthenticationFilter;
import com.construrrenta.api_gateway.infrastructure.security.filters.JwtValidationFilter;
import com.construrrenta.api_gateway.infrastructure.security.services.CustomUserDetailsService;

import org.springframework.http.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtValidationFilter jwtValidationFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenAdapter jwtTokenAdapter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenAdapter);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
                // 1. REGLAS PÚBLICAS (Lo que cualquiera puede ver)
                auth.requestMatchers("/api/v1/auth/**").permitAll();
                auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
                auth.requestMatchers(HttpMethod.GET, "/api/v1/tools/**").permitAll(); // Ver herramientas es público

                // 2. REGLAS DE ROLES (Lo específico)
                // Aquí va la línea que te causaba el error (ANTES del anyRequest)
                auth.requestMatchers("/api/v1/admin/reports/**").hasRole("ADMIN");
                auth.requestMatchers("/api/v1/bookings/provider/**").hasAnyRole("PROVIDER", "ADMIN");
                
                auth.requestMatchers(HttpMethod.PUT, "/users/**").authenticated();
                auth.requestMatchers("/api/v1/users/**").hasRole("ADMIN");

                // 3. EL CIERRE (El "Else" final)
                // Esto SIEMPRE debe ir al final. Nada puede ir después de esta línea.
                auth.anyRequest().authenticated();
            })
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
            "http://localhost:5173",
            "http://localhost:5174"
        )); 
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}