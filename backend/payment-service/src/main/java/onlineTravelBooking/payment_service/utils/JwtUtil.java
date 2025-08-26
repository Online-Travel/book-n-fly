package onlineTravelBooking.payment_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.hmacShaKeyFor("IdpTeamE'sVerySecureSecretKeyForOurProject!".getBytes());
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Extract role from token
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public Long extractUserId(String token){
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
            getClaims(token);
            return true;
        } catch (Exception e) {
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
