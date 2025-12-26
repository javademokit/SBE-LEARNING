package learningplatform.learningplatform.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document("users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private String status = "AU"; // AU = Active User, BL = Blocked

    private List<String> purchasedCourses = new ArrayList<>();

    private List<String> roles = new ArrayList<>();

    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles.add("USER"); // default role
        this.status = "AU";

    }

    public String getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getPurchasedCourses() { return purchasedCourses; }
    public void setPurchasedCourses(List<String> purchasedCourses) { this.purchasedCourses = purchasedCourses; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    // Helper methods
    public void addPurchasedCourse(String courseId) {
        if (!purchasedCourses.contains(courseId)) {
            purchasedCourses.add(courseId);
        }
    }

    public void addRole(String role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }

    // Check if a user already purchased a specific course
    public boolean hasPurchasedCourse(String courseId) {
        return purchasedCourses != null && purchasedCourses.contains(courseId);
    }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isBlocked() { return "BL".equalsIgnoreCase(status); }

}
