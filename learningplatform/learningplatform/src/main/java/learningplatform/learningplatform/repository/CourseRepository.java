package learningplatform.learningplatform.repository;


import learningplatform.learningplatform.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {
public List<Course> findByTitleContainingIgnoreCase(String title);
}
