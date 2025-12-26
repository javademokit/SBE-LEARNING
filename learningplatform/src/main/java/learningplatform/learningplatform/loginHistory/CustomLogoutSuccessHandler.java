package learningplatform.learningplatform.loginHistory;


import learningplatform.learningplatform.repository.LoginHistoryRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final LoginHistoryRepository repo;

    public CustomLogoutSuccessHandler(LoginHistoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
            throws IOException {
        if (auth != null) {
            String username = auth.getName();
            String ip = request.getRemoteAddr();
            repo.save(new LoginHistory(username, ip, "LOGOUT"));
            System.out.println("🚪 User logged out: " + username + " from " + ip);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
