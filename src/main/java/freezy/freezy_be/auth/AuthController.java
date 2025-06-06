package freezy.freezy_be.auth;

import freezy.freezy_be.cloudinary.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AppUserService appUserService;
    private final CloudinaryService cloudinaryService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/current-user")
    public AppUser getCurrentUser(@AuthenticationPrincipal AppUser appUser) {
        return appUser;
    }
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(
            @RequestPart("username") String username,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {

        String imageUrl = null;

        if (profileImage != null && !profileImage.isEmpty()) {
            imageUrl = cloudinaryService.upload(profileImage);
        }

        AppUser user = appUserService.registerUser(username, email, password, imageUrl, Set.of(Role.ROLE_USER));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request:");
        String token = appUserService.authenticateUser(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        // Recupera anche l'utente per inviare i ruoli
        AppUser user = appUserService.loadUserByUsername(loginRequest.getUsername());

        return ResponseEntity.ok(new AuthResponse(token, user.getRoles()));
    }
}
