package learningplatform.learningplatform.AuthController;

import learningplatform.learningplatform.interceptor.ApiMetricsStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class ApiMetricsController {

    private final ApiMetricsStore store;

    @Value("${spring.application.name:learning-platform}")
    private String serviceName;

    public ApiMetricsController(ApiMetricsStore store) {
        this.store = store;
    }

    @GetMapping("/metrics/api")
    public Map<String, Object> getApiMetrics() {

        List<Map<String, Object>> metrics = new ArrayList<>();

        store.requestCount.forEach((api, count) -> {

            long total = store.totalTime.getOrDefault(api, new AtomicLong()).get();
            long max = store.maxTime.getOrDefault(api, new AtomicLong()).get();
            long avg = count.get() == 0 ? 0 : total / count.get();

            Map<String, Object> apiData = new LinkedHashMap<>();
            apiData.put("api", api);
            apiData.put("totalRequests", count.get());
            apiData.put("avgTimeMs", avg);
            apiData.put("maxTimeMs", max);

            // ✅ add client IP stats
            apiData.put("clientIps",
                    store.clientIpStats.getOrDefault(api, Map.of()));

            metrics.add(apiData);
        });

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("service", serviceName);
        response.put("timestamp", System.currentTimeMillis());
        response.put("totalApis", metrics.size());
        response.put("metrics", metrics);

        return response;
    }
}
