package learningplatform.learningplatform.repository;


import learningplatform.learningplatform.model.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriberRepository extends MongoRepository<Subscriber, String> {
    boolean existsByEmail(String email);
}
