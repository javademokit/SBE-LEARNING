package learningplatform.learningplatform.RateLimitConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryRateLimiter {

    private final ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    @Autowired
    private RateLimitConfigProvider configProvider;

    public boolean allow(String ip, String api) {
        RateLimitConfig cfg = configProvider.getLimit(api);
        String key = ip + ":" + api;

        TokenBucket bucket = buckets.computeIfAbsent(
                key,
                k -> new TokenBucket(cfg.getRequests(), cfg.getWindowMs())
        );

        return bucket.allow();
    }
}
