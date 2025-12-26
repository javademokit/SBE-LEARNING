package learningplatform.learningplatform;

import learningplatform.learningplatform.model.Course;
import learningplatform.learningplatform.repository.CourseRepository;
import learningplatform.learningplatform.entity.User;
import learningplatform.learningplatform.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class SimpleAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(SimpleAuthApplication.class, args);
	}

	// ✅ Initialize default courses from courses.txt
	@Bean
	CommandLineRunner initCourses(CourseRepository repo) {
		return args -> {
			if (repo.count() == 0) {
				try (BufferedReader reader = new BufferedReader(
						new InputStreamReader(new ClassPathResource("courses.txt").getInputStream(), StandardCharsets.UTF_8))) {

					reader.lines().forEach(line -> {
						try {
							String[] parts = line.split(",");
							if (parts.length == 3) {
								String title = parts[0].trim();
								String description = parts[1].trim();
								double price = Double.parseDouble(parts[2].trim());
								String path = parts[3].trim();
								repo.save(new Course(title, description, price,path));
							}
						} catch (Exception e) {
							System.err.println("Error parsing line: " + line + " - " + e.getMessage());
						}
					});
					System.out.println("✅ Initialized default courses from courses.txt!");
				} catch (Exception e) {
					System.err.println("⚠️ Failed to read courses.txt: " + e.getMessage());
				}
			} else {
				System.out.println("✅ Courses already exist in the database. Skipping initialization.");
			}
		};
	}

	// ✅ Initialize default ADMIN user if not exists
	@Bean
	CommandLineRunner initAdmin(UserRepository userRepo, BCryptPasswordEncoder encoder) {
		return args -> {
			if (userRepo.findByUsername("admin").isEmpty()) {
				User admin = new User("admin", encoder.encode("admin123"), "admin@example.com");
				admin.addRole("ADMIN"); // ensure ADMIN role
				userRepo.save(admin);
				System.out.println("✅ Default admin created → username=admin, password=admin123");
			} else {
				System.out.println("✅ Admin user already exists.");
			}
		};
	}
}
