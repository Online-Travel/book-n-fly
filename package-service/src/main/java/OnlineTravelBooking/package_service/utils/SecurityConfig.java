//package OnlineTravelBooking.package_service.utils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//
//    @Autowired
//    private JwtAuthFilter jwtAuthFilter;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        // Public endpoints (health checks, discovery)
//                        .requestMatchers("/actuator/**", "/health/**", "/eureka/**").permitAll()
//
//                        // Itinerary endpoints with role-based access
//                        .requestMatchers("/api/itineraries").permitAll() // GET all itineraries - public
//                        .requestMatchers("/api/itineraries/{id}").permitAll() // GET specific itinerary - public
//                        .requestMatchers("/api/itineraries/user/{userId}").hasAnyRole("TRAVELER", "TRAVEL AGENT", "ADMIN") // GET user itineraries
//
//                        // Travel Package endpoints with role-based access
//                        .requestMatchers("/api/packages").permitAll() // GET all packages - public
//                        .requestMatchers("/api/packages/active").permitAll() // GET active packages - public
//                        .requestMatchers("/api/packages/{id}").permitAll() // GET specific package - public
//                        .requestMatchers("/api/packages/destination").permitAll() // Search by destination - public
//                        .requestMatchers("/api/packages/price").permitAll() // Search by price - public
//
//                        // POST, PUT, PATCH, DELETE operations are secured via @PreAuthorize annotations
//                        // in the controllers, but we can add additional layer here if needed
//                        .requestMatchers( "/api/itineraries").hasAnyRole("TRAVEL AGENT", "TRAVELER")
//                        .requestMatchers("/api/itineraries/{id}").hasAnyRole("TRAVEL AGENT", "ADMIN", "TRAVELER")
//                        .requestMatchers( "/api/itineraries/{id}/status").hasAnyRole("TRAVEL AGENT", "ADMIN", "TRAVELER")
//                        .requestMatchers( "/api/itineraries/{id}").hasAnyRole("TRAVEL AGENT", "ADMIN", "TRAVELER")
//
//                        .requestMatchers( "/api/packages").hasRole("TRAVEL AGENT")
//                        .requestMatchers( "/api/packages/{id}").hasAnyRole("TRAVEL AGENT", "ADMIN")
//                        .requestMatchers( "/api/packages/{id}").hasAnyRole("TRAVEL AGENT", "ADMIN")
//                        .requestMatchers( "/api/packages/{id}/hard").hasRole("ADMIN")
//
//                        // All other requests need authentication
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        // Add JWT filter before Spring Security's authentication filter
//        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//}

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
                        .requestMatchers(HttpMethod.POST, "/api/itineraries").hasAnyRole("TRAVEL AGENT", "TRAVELER")
                        .requestMatchers(HttpMethod.PUT, "/api/itineraries/{id}").hasAnyRole("TRAVEL AGENT", "ADMIN", "TRAVELER")
                        .requestMatchers(HttpMethod.PATCH, "/api/itineraries/{id}/status").hasAnyRole("TRAVEL AGENT", "ADMIN", "TRAVELER")
                        .requestMatchers(HttpMethod.DELETE, "/api/itineraries/{id}").hasAnyRole("TRAVEL AGENT", "ADMIN", "TRAVELER")

                        .requestMatchers(HttpMethod.POST, "/api/packages").hasRole("TRAVEL AGENT")
                        .requestMatchers(HttpMethod.PUT, "/api/packages/{id}").hasAnyRole("TRAVEL AGENT", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/packages/{id}").hasAnyRole("TRAVEL AGENT", "ADMIN")
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