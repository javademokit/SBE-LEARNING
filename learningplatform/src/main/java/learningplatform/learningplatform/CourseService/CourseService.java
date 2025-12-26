package learningplatform.learningplatform.CourseService;
import learningplatform.learningplatform.entity.User;
import learningplatform.learningplatform.model.Course;
import learningplatform.learningplatform.repository.CourseRepository;
import learningplatform.learningplatform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepo;
    private final UserRepository userRepo;

    public CourseService(CourseRepository courseRepo, UserRepository userRepo) {
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
    }

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }
    public List<Course> findByTitleContainingIgnoreCase(String title) {
        return courseRepo.findByTitleContainingIgnoreCase(title);
    }


    public Optional<Course> getCourseById(String id) {
        return courseRepo.findById(id);
    }


    public boolean deleteCourse(String id) {
        return courseRepo.existsById(id);
    }

    public void addCourse(Course course) {
        courseRepo.save(course);
    }

    public List<Course> getUserCourses(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return courseRepo.findAll().stream()
                .filter(c -> user.getPurchasedCourses().contains(c.getId()))
                .toList();
    }
    public void buyCourse(String username, String courseId) {
        boolean alreadyPurchased = userRepo.existsByUsernameAndPurchasedCoursesContains(username, courseId);
        if (alreadyPurchased) {
            throw new RuntimeException("You have already purchased this course!");
        }
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        user.addPurchasedCourse(courseId);
        userRepo.save(user);
    }




}
