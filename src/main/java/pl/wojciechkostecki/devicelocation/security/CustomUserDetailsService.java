package pl.wojciechkostecki.devicelocation.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wojciechkostecki.devicelocation.model.AppUser;
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
        AppUser appUser = userRepository.findByUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException(format("User %s not found",login)));
        return userToUserDetails(appUser);
    }

    private UserDetails userToUserDetails(AppUser appUser) {
        return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), getUserAuthorities(appUser));
    }

    private Collection<? extends GrantedAuthority> getUserAuthorities(AppUser appUser) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        appUser.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName().getValue()));});

        return authorities;
    }
}
