package learningplatform.learningplatform.AuthController;


import learningplatform.learningplatform.CourseService.CourseService;
import learningplatform.learningplatform.entity.User;
import learningplatform.learningplatform.model.Course;
import learningplatform.learningplatform.repository.CourseRepository;
import learningplatform.learningplatform.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {

    private final UserRepository userRepo;

    private final CourseService courseService;

    public AdminController(UserRepository userRepo,CourseService courseService) {
        this.userRepo = userRepo;
        this.courseService=courseService;;
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> dashboard() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/dashboard1")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> dashboard1() {
        List<User> users = userRepo.findAll();
        List<Course> courses = courseService.getAllCourses();

        return ResponseEntity.ok(Map.of(
                "users", users,
                "courses", courses
        ));
    }

    @PutMapping("/block/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> blockUser(@PathVariable String username) {
        Optional<User> userOpt = userRepo.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        User user = userOpt.get();
        user.setStatus("BL");
        userRepo.save(user);
        return ResponseEntity.ok("User " + username + " has been blocked");
    }

    @PutMapping("/unblock/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> unblockUser(@PathVariable String username) {
        Optional<User> userOpt = userRepo.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        User user = userOpt.get();
        user.setStatus("AU");
        userRepo.save(user);
        return ResponseEntity.ok("User " + username + " has been unblocked");
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        Optional<User> userOpt = userRepo.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        userRepo.delete(userOpt.get());
        return ResponseEntity.ok("User " + username + " has been removed from DB");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return ResponseEntity.ok(Map.of("message", "Course added successfully!"));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @DeleteMapping("/courses/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCourse(@PathVariable String id) {
        Optional<Course> courseOpt = courseService.getCourseById(id);
        if (courseOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Course not found");
        }
        courseService.deleteCourse(id);
        return ResponseEntity.ok(Map.of("message", "Course deleted successfully!"));
    }
}
