package cphbusiness.noinputs.main.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String pKey;

    public Long getUserIdFromJwtToken(String jwtToken) {
        SecretKey key = Keys.hmacShaKeyFor(pKey.getBytes());
        Jws<Claims> jwtTokenParsed;
        jwtTokenParsed = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken);

        return Long.parseLong(jwtTokenParsed.getHeader().get("id").toString());
    }
}
