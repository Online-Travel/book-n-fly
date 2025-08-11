package onlineTravelBooking.review_service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
                        // Public endpoints (health checks, discovery, swagger)
                        .requestMatchers("/actuator/**", "/eureka/**").permitAll()

                        // Review endpoints with role-based access
                        .requestMatchers("/api/reviews").hasRole("TRAVELER") // POST - add review
                        .requestMatchers("/api/reviews/my").hasRole("TRAVELER") // GET - user's own reviews
                        .requestMatchers("/api/reviews/hotel/**").authenticated() // GET - hotel reviews (all authenticated users)
                        .requestMatchers("/api/reviews/{reviewId}").hasRole("TRAVELER") // PUT/DELETE - update/delete review
                        .requestMatchers("/api/reviews/all/**").hasRole("ADMIN") // GET - all reviews (admin only)

                        // Support Ticket endpoints with role-based access
                        .requestMatchers("/api/support-tickets").hasRole("TRAVELER") // POST - create ticket
                        .requestMatchers("/api/support-tickets/my").hasRole("TRAVELER") // GET - user's tickets
                        .requestMatchers("/api/support-tickets/{ticketId}").authenticated() // GET - single ticket (any authenticated user)
                        .requestMatchers("/api/support-tickets/all").hasRole("ADMIN") // GET - all tickets (admin)
                        .requestMatchers("/api/support-tickets/open").hasRole("ADMIN") // GET - open tickets (admin)
                        .requestMatchers("/api/support-tickets/{ticketId}/assign/{agentId}").hasRole("ADMIN") // PUT - assign ticket (admin)
                        .requestMatchers("/api/support-tickets/{ticketId}/close").hasRole("TRAVEL AGENT") // PUT - close ticket (agent)
                        .requestMatchers("/api/support-tickets/assigned").hasRole("TRAVEL AGENT") // GET - assigned tickets (agent)

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add JWT filter before Spring Security's authentication filter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
