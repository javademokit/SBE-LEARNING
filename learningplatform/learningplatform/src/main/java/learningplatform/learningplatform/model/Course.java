package learningplatform.learningplatform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("courses")
public class Course {
    @Id
    private String id;
    private String title;
    private String description;
    private double price;
    private  String path;

    // ✅ New field: course validity date (defaults to 12 months from creation)
    private LocalDate validUntil;

    public Course() {
        // Set default validity (12 months from today)
        this.validUntil = LocalDate.now().plusMonths(12);
    }

    public Course(String title, String description, double price,String path ) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.path=path;
        this.validUntil = LocalDate.now().plusMonths(12); // default 12-month validity
    }

    // Getters and Setters
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public LocalDate getValidUntil() { return validUntil; }
    public void setValidUntil(LocalDate validUntil) { this.validUntil = validUntil; }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
