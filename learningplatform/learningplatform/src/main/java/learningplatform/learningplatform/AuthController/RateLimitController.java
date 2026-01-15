package learningplatform.learningplatform.AuthController;

import learningplatform.learningplatform.RateLimitConfig.RateLimitConfigProvider;
import learningplatform.learningplatform.RateLimitConfig.RateLimitMetricsStore;
import learningplatform.learningplatform.RateLimitConfig.RateLimitUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // ✅ NO credentials
@RequestMapping("/rate-limit")
public class RateLimitController {

    @Autowired
    private RateLimitMetricsStore store;

    @Autowired
    private RateLimitConfigProvider rateLimitConfigProvider;

    /* ===================== METRICS ===================== */

    // GET /rate-limit/metrics
    @GetMapping("/metrics")
    public Map<String, Object> metrics() {
        return Map.of(
                "blockedByApi", store.blockedByApi,
                "blockedByIp", store.blockedByIp
        );
    }

    /* ===================== ADMIN UPDATE ===================== */

    // POST /rate-limit/admin
    @PostMapping("/admin")
    public Map<String, String> updateRateLimit(
            @RequestBody RateLimitUpdateRequest request
    ) {
        rateLimitConfigProvider.update(
                request.getApi(),
                request.getRequests(),
                request.getWindowMs()
        );
        return Map.of("message", "✅ Rate limit updated for " + request.getApi());
    }
}
