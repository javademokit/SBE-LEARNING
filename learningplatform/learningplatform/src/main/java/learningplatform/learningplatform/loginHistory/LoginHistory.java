package learningplatform.learningplatform.loginHistory;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document("login_history")
public class LoginHistory {

    @Id
    private String id;

    private String username;
    private String ipAddress;
    private LocalDateTime loginTime;
    private String eventType; // "LOGIN" or "LOGOUT"

    public LoginHistory() {}

    public LoginHistory(String username, String ipAddress, String eventType) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.eventType = eventType;
        this.loginTime = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public LocalDateTime getLoginTime() { return loginTime; }
    public void setLoginTime(LocalDateTime loginTime) { this.loginTime = loginTime; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
}
