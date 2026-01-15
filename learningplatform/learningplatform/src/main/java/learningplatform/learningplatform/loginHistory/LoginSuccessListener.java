package learningplatform.learningplatform.loginHistory;

import learningplatform.learningplatform.repository.LoginHistoryRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final LoginHistoryRepository loginHistoryRepository;
    private final HttpServletRequest request;

    public LoginSuccessListener(LoginHistoryRepository loginHistoryRepository, HttpServletRequest request) {
        this.loginHistoryRepository = loginHistoryRepository;
        this.request = request;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        String ip = request.getRemoteAddr();
        LoginHistory history = new LoginHistory(username, getClientIp(ip), "LOGIN");
        loginHistoryRepository.save(history);
        System.out.println("✅ User login tracked: " + username + " from " + ip);
    }

    private String getClientIp(String request) {
        String xff = request;
        if (xff != null && !xff.isEmpty()) {
            return xff.split(",")[0].trim();
        }

        String realIp = request;
        if (realIp != null && !realIp.isEmpty()) {
            return realIp;
        }

        String ip = request;
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
