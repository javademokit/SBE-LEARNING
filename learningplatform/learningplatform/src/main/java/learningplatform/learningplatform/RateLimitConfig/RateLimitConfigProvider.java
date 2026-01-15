package learningplatform.learningplatform.RateLimitConfig;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitConfigProvider {

    private final Map<String, RateLimitConfig> apiLimits = new ConcurrentHashMap<>();

    @PostConstruct
    public void load() {
        // API → Rate limit
        apiLimits.put("/metrics/api", new RateLimitConfig(1, 1000)); // 5/sec
        apiLimits.put("/api/courses/all/java", new RateLimitConfig(1, 1000)); // 10/sec
        apiLimits.put("/api/admin/dashboard", new RateLimitConfig(1, 1000)); // 2/sec
    }

    public RateLimitConfig getLimit(String api) {
        return apiLimits.getOrDefault(
                api, new RateLimitConfig(20, 1000)
        );
    }

    // 🔥 Runtime update (from DB / Admin API)
    public void update(String api, int requests, long windowMs) {
        apiLimits.put(api, new RateLimitConfig(requests, windowMs));
    }
}
