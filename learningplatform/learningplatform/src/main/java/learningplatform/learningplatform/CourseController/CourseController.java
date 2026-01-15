package learningplatform.learningplatform.CourseController;


import learningplatform.learningplatform.CourseService.CourseService;
import learningplatform.learningplatform.entity.User;
import learningplatform.learningplatform.model.Course;
import learningplatform.learningplatform.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")

public class CourseController {

    private final CourseService courseService;
    private final UserRepository userRepository;
    public CourseController(CourseService courseService, UserRepository userRepository) {
        this.courseService = courseService;
        this.userRepository = userRepository;
    }

    // Admin or startup init can use this
    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return ResponseEntity.ok("Course added successfully!");
    }

    // List all available courses
    @GetMapping("/all/{title}")
    public ResponseEntity<List<Course>> getfindByTitleContainingIgnoreCase(@PathVariable String title) {
        return ResponseEntity.ok(courseService.findByTitleContainingIgnoreCase(title));
    }


    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }



    // Buy a course
    @PostMapping("/buy/{courseId}")
    public ResponseEntity<?> buyCourse(@PathVariable String courseId, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Please login first");
        }
        courseService.buyCourse(auth.getName(), courseId);
        return ResponseEntity.ok("Course purchased successfully!");
    }

    // List user's purchased courses
    @GetMapping("/my")
    public ResponseEntity<?> myCourses(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Please login first");
        }
        return ResponseEntity.ok(courseService.getUserCourses(auth.getName()));
    }



}
