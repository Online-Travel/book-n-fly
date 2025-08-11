package onlineTravelBooking.payment_service.feignclient;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class FeignAuthInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // If credentials contains the JWT token string, extract from there
            Object credentials = authentication.getCredentials();
            if (credentials instanceof String) {
                String token = (String) credentials;
                template.header("Authorization", "Bearer " + token);
            }
            else {
                // fallback or log warning if token isn't found
                // Optionally, handle JwtAuthenticationToken case like:
                // if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                //    String token = jwtAuth.getToken().getTokenValue();
                //    template.header("Authorization", "Bearer " + token);
                // }
            }
        }
    }

}
