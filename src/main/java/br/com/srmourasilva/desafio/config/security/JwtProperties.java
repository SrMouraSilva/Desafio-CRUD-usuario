package br.com.srmourasilva.desafio.config.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtProperties {

    @Value("${jwt.audience}")
    private String audience;

    @Value("${jwt.time}")
    private int timeForExpirationInSeconds;

    @Value("${jwt.secret}")
    private String secret;

    public int getTimeForExpirationInSeconds() {
        return timeForExpirationInSeconds;
    }

    public Date generateNextExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, getTimeForExpirationInSeconds());
        return calendar.getTime();
    }

    public String getAudience() {
        return audience;
    }

    public SecretKey getKey() {
        String base64Secret = Base64.getEncoder().encodeToString(secret.getBytes());
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
    }
}
