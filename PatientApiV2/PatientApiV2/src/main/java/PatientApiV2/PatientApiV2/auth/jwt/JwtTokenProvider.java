package PatientApiV2.PatientApiV2.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(UserDetails user) {
        String token;
        Date iat = new Date();
        Date exp = new Date(iat.getTime() + jwtExpiration);
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        token = Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(iat)
                .expiration(exp)
                .signWith(key)
                .compact();

        return token;
    }
}