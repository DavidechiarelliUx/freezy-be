package freezy.freezy_be.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Admin
        Optional<AppUser> adminUser = appUserService.findByUsername("admin");
        if (adminUser.isEmpty()) {
            appUserService.registerUser("admin", "admin@email.com", "adminpwd", Set.of(Role.ROLE_ADMIN));
        }

        // User
        Optional<AppUser> normalUser = appUserService.findByUsername("user");
        if (normalUser.isEmpty()) {
            appUserService.registerUser("user", "user@email.com", "userpwd", Set.of(Role.ROLE_USER));
        }

        // Seller
        Optional<AppUser> normalSeller = appUserService.findByUsername("seller");
        if (normalSeller.isEmpty()) {
            appUserService.registerUser("seller", "seller@email.com", "sellerpwd", Set.of(Role.ROLE_SELLER));
        }
    }
}
