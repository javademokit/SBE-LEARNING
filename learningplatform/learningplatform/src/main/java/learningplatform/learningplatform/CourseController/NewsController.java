package learningplatform.learningplatform.CourseController;


import learningplatform.learningplatform.CourseService.EmailService;
import learningplatform.learningplatform.model.News;
import learningplatform.learningplatform.repository.NewsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
public class NewsController {

    private final NewsRepository newsRepo;
    private final EmailService emailService;

    public NewsController(NewsRepository newsRepo, EmailService emailService) {
        this.newsRepo = newsRepo;
        this.emailService = emailService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNews(@RequestBody News news) {
        newsRepo.save(news);
        emailService.sendNewsToAll(news.getTitle(), news.getContent());
        return ResponseEntity.ok("News posted and emails sent!");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllNews() {
        return ResponseEntity.ok(newsRepo.findAll());
    }
}
