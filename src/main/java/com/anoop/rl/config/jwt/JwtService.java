package com.anoop.rl.config.jwt;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.anoop.rl.model.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {

    private final String SECRET_KEY;

    public JwtService(Dotenv dotenv) {
        this.SECRET_KEY = dotenv.get("JWT_SECRET_KEY");
        //this.SECRET_KEY ="b8739d66c52d1969c583b49fae82fcf4ea9f72e97d22f1304dc5a90dd02d94da";
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user){
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
        .parser()
        .verifyWith(getSigninKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }
    
    public String generateToken(UserEntity user){
        String token = Jwts
        .builder()
        .subject(user.getUsername())
        .claim("userId", user.getUserId())
        .claim("email", user.getEmail())
        .claim("role", user.getRole())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
        .signWith(getSigninKey())
        .compact();

        return token;
    }

    private SecretKey getSigninKey(){
        byte [] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
