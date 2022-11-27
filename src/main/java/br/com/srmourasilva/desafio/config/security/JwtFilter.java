package br.com.srmourasilva.desafio.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService service;

    public JwtFilter(JwtService jwtService, UserDetailsService service) {
        this.jwtService = jwtService;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = getToken(request);

        if (token.isPresent()) {
            jwtService.validate(token.get());

            UsernamePasswordAuthenticationToken userToken = getUsernamePasswordAuthenticationToken(request, token.get());

            SecurityContextHolder.getContext().setAuthentication(userToken);
        }

        filterChain.doFilter(request,response);
    }

    private Optional<String> getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer")) {
            return Optional.of(header.split(" ")[1]);
        }

        return Optional.empty();
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(HttpServletRequest request, String token) {
        String login = jwtService.getUsername(token);
        UserDetails user = service.loadUserByUsername(login);

        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userToken;
    }
}
