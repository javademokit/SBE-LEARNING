package learningplatform.learningplatform.repository;

import learningplatform.learningplatform.model.RouteLink;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RouteLinkRepository extends MongoRepository<RouteLink, String> { }
