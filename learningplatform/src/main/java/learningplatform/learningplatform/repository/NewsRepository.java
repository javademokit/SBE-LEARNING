package learningplatform.learningplatform.repository;


import learningplatform.learningplatform.model.News;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News, String> { }

