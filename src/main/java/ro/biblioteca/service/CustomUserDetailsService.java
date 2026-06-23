package ro.biblioteca.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.biblioteca.repository.UtilizatorRepository;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilizatorRepository utilizatorRepository;

    public CustomUserDetailsService(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var utilizator = utilizatorRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizator negasit: " + username));

        var authorities = utilizator.getRoluri().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNume()))
                .collect(Collectors.toSet());

        return User.builder()
                .username(utilizator.getUsername())
                .password(utilizator.getParola())
                .authorities(authorities)
                .disabled(!Boolean.TRUE.equals(utilizator.getActiv()))
                .build();
    }
}
