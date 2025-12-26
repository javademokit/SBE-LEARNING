package learningplatform.learningplatform.CourseController;

import learningplatform.learningplatform.model.RouteLink;
import learningplatform.learningplatform.model.RouteRequest;
import learningplatform.learningplatform.repository.RouteLinkRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/routes")
public class RouteLinkController {

    private final RouteLinkRepository repo;

    public RouteLinkController(RouteLinkRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/add")
    public List<RouteLink> addRoutes(@RequestBody RouteRequest request) {
        return repo.saveAll(request.getRoutes());
    }

    @GetMapping("/all")
    public List<RouteLink> getAll() {
        return repo.findAll();
    }

    @GetMapping("/paths")
    public List<String> getAllPaths() {
        return repo.findAll()
                .stream()
                .map(RouteLink::getPath)
                .toList();
    }

}
