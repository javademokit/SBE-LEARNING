package learningplatform.learningplatform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "routes")
public class RouteLink {

    @Id
    private String id;

    private String path;
    private String component;
    private String title;

    private String category; // optional, nullable

    // required for JSON → POJO → MongoDB
    public RouteLink() {}

    public RouteLink(String path, String component, String title, String category) {
        this.path = path;
        this.component = component;
        this.title = title;
        this.category = category;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
