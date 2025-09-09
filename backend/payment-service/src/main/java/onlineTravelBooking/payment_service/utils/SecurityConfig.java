package onlineTravelBooking.payment_service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .requestMatchers("/actuator/**", "/health/**","/eureka/**").permitAll()

                        .requestMatchers("/payments/create-order").permitAll()
                        // Payment endpoints with role-based access
                        .requestMatchers("/payments/greet").hasRole("ADMIN")
                        .requestMatchers("/payments/create/**").hasRole("TRAVELER")
                        .requestMatchers("/payments/user/**").hasAnyRole("TRAVELER","TRAVEL_AGENT")
                        .requestMatchers("/payments/booking/**").hasAnyRole("TRAVELER","TRAVEL_AGENT")
                        .requestMatchers("/payments/update/**").hasAnyRole("TRAVELER", "ADMIN","TRAVEL_AGENT")
                        .requestMatchers("/payments/admin/**").hasRole("ADMIN")
                        .requestMatchers("/payments/{payment_id}").hasAnyRole("TRAVELER", "ADMIN","TRAVEL_AGENT")
                        .requestMatchers("/payments/getpayments/**").hasAnyRole("TRAVELER", "ADMIN","TRAVEL_AGENT")

                        // Invoice endpoints with role-based access
                        .requestMatchers("/invoice/mail/**").hasAnyRole("TRAVELER","TRAVEL_AGENT")
                        .requestMatchers("/invoice/admin/**").hasRole("ADMIN")

                        // All other requests need authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add JWT filter before Spring Security's authentication filter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
