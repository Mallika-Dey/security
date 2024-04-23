package com.example.security.sec1.auth;

import com.example.security.sec1.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTUtils {
    //replace secret key
    public static final String SECRET_KEY = "jwt_secret_key";

    public boolean isTokenExpired(String token) {
        Claims claims = getClaim(token);
        return claims.getExpiration().before(new Date());
    }

    public String extractUser(String token) {
        return getClaim(token).getSubject();
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        Claims claims = getClaim(token);
        List<String> roles = claims.get("roles", List.class);

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toUpperCase()))
                .collect(Collectors.toList());
    }

    public String generateToken(User user) {
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 600 * 240))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

  /*  private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }*/

    private Claims getClaim(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
