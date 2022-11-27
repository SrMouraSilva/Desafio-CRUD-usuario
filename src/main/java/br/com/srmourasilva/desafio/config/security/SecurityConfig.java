package br.com.srmourasilva.desafio.config.security;

import br.com.srmourasilva.desafio.usecase.user.password.Argon2HashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableGlobalMethodSecurity(
//    prePostEnabled = true,
//    jsr250Enabled = true,
    securedEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService service;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    // Documentation
                    .antMatchers("/documentation/**").permitAll()
                    .antMatchers("/v3/api-docs/**").permitAll()
                    // Authentication
                    .antMatchers("/auth").permitAll()
                    // API
                    .antMatchers("/**").authenticated()
                    .anyRequest().denyAll()
            .and()
                .httpBasic().disable();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtService, service);
    }

    @Bean
    public Argon2HashGenerator hashPassword() {
        return new Argon2HashGenerator();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return hashPassword().getEncoder();
    }

    @Bean
    public AuthenticationManager configAuthRules(HttpSecurity http, PasswordEncoder encoder, UserDetailsServiceImpl userDetaislServices) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetaislServices)
                .passwordEncoder(encoder)
                .and()
                .build();
    }
}