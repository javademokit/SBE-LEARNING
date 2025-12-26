package learningplatform.learningplatform.AuthController;


import learningplatform.learningplatform.loginHistory.LoginHistory;
import learningplatform.learningplatform.repository.LoginHistoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/logins")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LoginHistoryController {

    private final LoginHistoryRepository repo;

    public LoginHistoryController(LoginHistoryRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<LoginHistory> getAllLogins() {
        return repo.findAll();
    }
    // ✅ Get login/logout history by username
    @GetMapping("/{username}")
    public List<LoginHistory> getLogsByUsername(@PathVariable String username) {
        return repo.findByUsername(username);
    }
}
