package learningplatform.learningplatform.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subscribers")
public class Subscriber {
    @Id
    private String id;
    private String email;

    public Subscriber() {}
    public Subscriber(String email) {
        this.email = email;
    }

    public String getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
