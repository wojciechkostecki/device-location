package pl.wojciechkostecki.devicelocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.wojciechkostecki.devicelocation.model.Role;
import pl.wojciechkostecki.devicelocation.model.User;
import pl.wojciechkostecki.devicelocation.model.UserRole;
import pl.wojciechkostecki.devicelocation.repository.UserRepository;
import pl.wojciechkostecki.devicelocation.repository.UserRoleRepository;

@SpringBootApplication
public class DeviceLocationApplication {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DeviceLocationApplication(UserRoleRepository userRoleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(DeviceLocationApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterInitSetup() {
        saveRoles();
        saveAdmin();
    }

    private void saveAdmin() {
        if(!userRepository.findByUsername("admin").isPresent()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.getRoles().add(userRoleRepository.findByName(Role.ADMIN).get());
            user.getRoles().add(userRoleRepository.findByName(Role.USER).get());
            userRepository.save(user);
        }
    }

    private void saveRoles() {
        for (Role role : Role.values()) {
            if(!userRoleRepository.existsByName(role)) {
                UserRole userRole = new UserRole();
                userRole.setName(role);
                userRoleRepository.save(userRole);
            }
        }
    }
}
