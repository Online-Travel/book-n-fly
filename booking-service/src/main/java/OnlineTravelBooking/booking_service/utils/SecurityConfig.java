package OnlineTravelBooking.booking_service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (health checks, discovery)
                        .requestMatchers("/actuator/**", "/health/**", "/eureka/**").permitAll()

                        // Hotel endpoints with role-based access
                        .requestMatchers(HttpMethod.GET, "/api/hotels/**").permitAll() // Public hotel viewing
                        .requestMatchers(HttpMethod.POST, "/api/hotels").hasRole("HOTEL_MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/hotels/**").hasRole("HOTEL_MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/hotels/**").hasRole("ADMIN")

                        // Flight endpoints with role-based access
                        .requestMatchers(HttpMethod.GET, "/api/flights/**").permitAll() // Public flight viewing
                        .requestMatchers(HttpMethod.POST, "/api/flights").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/flights/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/flights/**").hasRole("ADMIN")

                        // Booking endpoints with role-based access
                        .requestMatchers(HttpMethod.GET, "/api/bookings/**").hasAnyRole("TRAVELER", "HOTEL_MANAGER", "TRAVEL AGENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/bookings/**").hasRole("TRAVELER")
                        .requestMatchers(HttpMethod.PUT, "/api/bookings/**").hasRole("TRAVELER")
                        .requestMatchers(HttpMethod.DELETE, "/api/bookings/**").hasRole("TRAVELER")
                        .requestMatchers(HttpMethod.GET,"/api/booking/greet").hasRole("TRAVELER")

                        // All other requests need authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add JWT filter before Spring Security's authentication filter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
