
package OnlineTravelBooking.package_service.utils;

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

                        // GET endpoints - public access
                        .requestMatchers(HttpMethod.GET, "/api/itineraries").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/itineraries/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/itineraries/user/{userId}").permitAll()

                        // Travel Package GET endpoints - public access
                        .requestMatchers(HttpMethod.GET, "/api/packages").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/packages/active").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/packages/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/packages/destination").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/packages/price").permitAll()

                        // POST, PUT, PATCH, DELETE operations with proper HTTP method syntax
                        .requestMatchers(HttpMethod.POST, "/api/itineraries").hasAnyRole("TRAVEL_AGENT", "TRAVELER")
                        .requestMatchers(HttpMethod.PUT, "/api/itineraries/{id}").hasAnyRole("TRAVEL_AGENT", "ADMIN", "TRAVELER")
                        .requestMatchers(HttpMethod.PATCH, "/api/itineraries/{id}/status").hasAnyRole("TRAVEL_AGENT", "ADMIN", "TRAVELER")
                        .requestMatchers(HttpMethod.DELETE, "/api/itineraries/{id}").hasAnyRole("TRAVEL_AGENT", "ADMIN", "TRAVELER")

                        .requestMatchers(HttpMethod.POST, "/api/packages").hasRole("TRAVEL_AGENT")
                        .requestMatchers(HttpMethod.PUT, "/api/packages/{id}").hasAnyRole("TRAVEL_AGENT", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/packages/{id}").hasAnyRole("TRAVEL_AGENT", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/packages/{id}/hard").hasRole("ADMIN")

                        // All other requests need authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add JWT filter before Spring Security's authentication filter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}