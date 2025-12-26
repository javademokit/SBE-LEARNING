package learningplatform.learningplatform.UserService;

import learningplatform.learningplatform.entity.User;
import learningplatform.learningplatform.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Register new user (default role = USER)
    public User registerUser(String username, String email, String password) {
        if (repo.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User(username, encoder.encode(password), email);
        user.addRole("ADMIN"); // default role

        return repo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // If no roles exist, default to USER
        List<SimpleGrantedAuthority> authorities = user.getRoles().isEmpty()
                ? List.of(new SimpleGrantedAuthority("ROLE_USER"))
                : user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    public BCryptPasswordEncoder getEncoder() {
        return encoder;
    }
}
