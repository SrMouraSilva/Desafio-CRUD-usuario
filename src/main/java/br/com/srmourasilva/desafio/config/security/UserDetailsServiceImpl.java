package br.com.srmourasilva.desafio.config.security;

import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static br.com.srmourasilva.desafio.validation.mesage.ArchitectureMessage.ENTITY_NOT_FOUND;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(ENTITY_NOT_FOUND));

        return new UserDetailsImpl(user);
    }
}
