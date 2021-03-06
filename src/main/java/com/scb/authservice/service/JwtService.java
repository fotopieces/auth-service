package com.scb.authservice.service;

import com.scb.authservice.dto.JwtParseResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class JwtService {

    private final String signingKey;

    @Autowired
    public JwtService(@Value("${security.jwt.signing-key}") String signingKey) {
        this.signingKey = signingKey;
    }

    public JwtParseResponseDto parseJwt(String token) {
        Objects.requireNonNull(token);
        Claims claims = Jwts.parser()
                .setSigningKey(signingKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        List<String> authorities = claims.get("authorities", List.class);

        return new JwtParseResponseDto(username, authorities);
    }
}
