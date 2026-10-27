package com.tecnouam.backend.security;

import com.tecnouam.backend.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final long EXPIRATION_TIME_MS = 1000 * 60 * 60; // 60 minutos

    private SecretKey getSigningKey() {
        String secret = System.getenv("JWT_SECRET_BASE64");
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("La variable de entorno JWT_SECRET_BASE64 no está configurada.");
        }
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("La clave secreta debe tener al menos 32 bytes (256 bits).");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Usuario usuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME_MS);

        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("role", usuario.getRol())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, Usuario usuario) {
        try {
            Claims claims = extractAllClaims(token);
            String username = claims.getSubject();
            boolean isExpired = claims.getExpiration().before(new Date());

            return (username.equals(usuario.getUsername()) && !isExpired && usuario.isActivo());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}