package learningplatform.learningplatform.AuthController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import learningplatform.learningplatform.UserService.UserService;
import learningplatform.learningplatform.entity.User;
import learningplatform.learningplatform.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final UserRepository userRepo;

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepo, UserService userService, AuthenticationManager authManager, UserRepository userRepository) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.authManager = authManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            userService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginReq, HttpServletRequest request) {

        Optional<User> userOpt = userRepo.findByUsername(loginReq.getUsername());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        User user = userOpt.get();
        if (user.isBlocked()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Your account is blocked. Please contact support."));
        }
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true); // ensure session created
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
       // return JSON with username (and optionally name/email)
        Map<String, String> response = new HashMap<>();
        response.put("username", loginReq.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized - please login");
        }
        return ResponseEntity.ok("Welcome, " + auth.getName() + "!");
    }

    //  New logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully!");
    }
}
