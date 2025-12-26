package learningplatform.learningplatform.repository;

import learningplatform.learningplatform.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
   boolean existsByUsernameAndPurchasedCoursesContains(String username, String courseId);

}
