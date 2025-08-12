package OnlineTravelBooking.package_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.hmacShaKeyFor("IdpTeamE'sVerySecureSecretKeyForOurProject!".getBytes());
    public String extractEmail(String token) {
        try {
            return getClaims(token).getSubject();
        } catch (Exception e) {
            System.out.println("Error Extracting email from token: "+e.getMessage());
            return  null;
        }
    }

    // Extract role from token
    public String extractRole(String token) {
        try {
            return getClaims(token).get("role", String.class);
        }
        catch (Exception e){
            System.err.println("Error extracting role from the token:"+e.getMessage());
            return null;
        }
    }

    public Long extractUserId(String token){
        try {
            Claims claims = getClaims(token);
            Object userIdClaim = claims.get("userId");
            if (userIdClaim != null) {
                if (userIdClaim instanceof Integer) {
                    return ((Integer) userIdClaim).longValue();
                } else if (userIdClaim instanceof Long) {
                    return (Long) userIdClaim;
                }
            }
        }
        catch (Exception e){
            System.err.println("Error extracting userId from token:"+e.getMessage());
        }
        return null;
    }

    // Validate token signature and expiration
    public boolean validateToken(String token) {
        try {
            String cleanToken=cleanToken(token);
            getClaims(cleanToken);
            return true;
        } catch (Exception e) {
            System.err.println("Token validation failed:"+e.getMessage());
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

    private String cleanToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }

        // Remove any whitespace and ensure no Bearer prefix
        String cleanedToken = token.trim();
        if (cleanedToken.toLowerCase().startsWith("bearer ")) {
            cleanedToken = cleanedToken.substring(7).trim();
        }

        return cleanedToken;
    }
}
