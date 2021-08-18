package pl.wojciechkostecki.devicelocation.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wojciechkostecki.devicelocation.model.User;
import pl.wojciechkostecki.devicelocation.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException(format("User %s not found",login)));
        return userToUserDetails(user);
    }

    private UserDetails userToUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getUserAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getUserAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName().getValue()));});

        return authorities;
    }
}
