package learningplatform.learningplatform.CourseController;


import learningplatform.learningplatform.model.Subscriber;
import learningplatform.learningplatform.repository.SubscriberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscribe")
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
public class SubscriptionController {

    private final SubscriberRepository subscriberRepo;

    public SubscriptionController(SubscriberRepository subscriberRepo) {
        this.subscriberRepo = subscriberRepo;
    }

    @PostMapping
    public ResponseEntity<?> subscribe(@RequestParam String email) {
        if (subscriberRepo.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Already subscribed!");
        }
        subscriberRepo.save(new Subscriber(email));
        return ResponseEntity.ok("Subscription successful for " + email);
    }
}
