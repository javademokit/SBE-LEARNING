package learningplatform.learningplatform.repository;


import learningplatform.learningplatform.loginHistory.LoginHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LoginHistoryRepository extends MongoRepository<LoginHistory, String> {
    List<LoginHistory> findByUsername(String username);

}
