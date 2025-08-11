package onlineTravelBooking.review_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.hmacShaKeyFor("IdpTeamE'sVerySecureSecretKeyForOurProject!".getBytes());
    public String extractEmail(String token) {
        if(token.startsWith("Bearer ")){
            token=token.substring(7);
        }
        return getClaims(token).getSubject();
    }

    // Extract role from token
    public String extractRole(String token) {
        if(token.startsWith("Bearer ")){
            token=token.substring(7);
        }
        return getClaims(token).get("role", String.class);
    }

    public Long extractUserId(String token){
        if(token.startsWith("Bearer ")){
            token=token.substring(7);
        }
        Claims claims=getClaims(token);
        Object userIdClaim=claims.get("userId");
        if(userIdClaim!=null){
            if(userIdClaim instanceof Integer){
                return ((Integer) userIdClaim).longValue();
            }
            else if(userIdClaim instanceof Long){
                return (Long) userIdClaim;
            }
        }
        return null;
    }

    // Validate token signature and expiration
    public boolean validateToken(String token) {
        try {
            if(token.startsWith("Bearer ")){
                token=token.substring(7);
            }
            getClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println("Token validation foiled"+e.getMessage());
            return false;
        }
    }

    // Extract all claims
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
