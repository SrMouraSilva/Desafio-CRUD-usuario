package br.com.srmourasilva.desafio.config;

import br.com.srmourasilva.desafio.usecase.user.password.Argon2HashGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    jsr250Enabled = true,
    securedEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    // Documentation
                    .antMatchers("/documentation/**").permitAll()
                    .antMatchers("/v3/api-docs/**").permitAll()
                    // API
                    //.antMatchers("/**").authenticated()
                    //.anyRequest().denyAll()
                    .anyRequest().permitAll()
                    //.anyRequest().authenticated()
            .and()
                .httpBasic().disable();
//                .and()
//                .httpBasic();
    }

    @Bean
    public Argon2HashGenerator hashPassword() {
        return new Argon2HashGenerator();
    }
}