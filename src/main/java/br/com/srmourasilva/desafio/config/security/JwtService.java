package br.com.srmourasilva.desafio.config.security;

import br.com.srmourasilva.desafio.dto.auth.TokenResponseDTO;
import br.com.srmourasilva.desafio.exception.TokenException;
import br.com.srmourasilva.desafio.validation.mesage.ArchitectureMessage;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtService {

    private final JwtProperties properties;

    public JwtService(JwtProperties properties) {
        this.properties = properties;
    }

    public TokenResponseDTO newToken(UserDetails user) {
        String token = Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(properties.generateNextExpirationDate())
            .setAudience(properties.getAudience())
            .addClaims(getCustomClaims(user))
            .signWith(properties.getKey())
            .compact();

        return new TokenResponseDTO(token, properties.getTimeForExpirationInSeconds());
    }

    private Map<String, Object> getCustomClaims(UserDetails user) {
        Map<String, Object> customClaims = new HashMap<>();
        customClaims.put("profile", getAuthority(user));
        return customClaims;
    }

    private String getAuthority(UserDetails user) {
        Optional<? extends GrantedAuthority> authority = user.getAuthorities().stream().findFirst();
        return authority.map(GrantedAuthority::getAuthority).orElse(null);
    }


    private JwtParser getParser() throws ExpiredJwtException {
        return Jwts
            .parserBuilder()
            .setSigningKey(properties.getKey())
            .build();
    }

    public void validate(String token) throws TokenException {
        try {
            Jws<Claims> headerClaimsJwt = getParser().parseClaimsJws(token);

            if (!properties.getAudience().equals(headerClaimsJwt.getBody().getAudience())) {
                throw new TokenException(ArchitectureMessage.INVALID_TOKEN_AUDIENCE);
            }
        } catch (ExpiredJwtException e) {
            throw new TokenException(ArchitectureMessage.INVALID_TOKEN_EXPIRED, e);
        } catch (UnsupportedJwtException | MalformedJwtException | SecurityException | IllegalArgumentException e) {
            throw new TokenException(ArchitectureMessage.INVALID_TOKEN, e);
        }
    }

    public String getUsername(String token) {
        return getParser().parseClaimsJws(token).getBody().getSubject();
    }
}
