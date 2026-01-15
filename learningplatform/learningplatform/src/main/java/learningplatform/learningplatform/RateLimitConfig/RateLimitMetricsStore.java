package learningplatform.learningplatform.RateLimitConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Component
public class RateLimitMetricsStore {


    public final ConcurrentHashMap<String, AtomicLong> blockedByApi = new ConcurrentHashMap<>();
    public final ConcurrentHashMap<String, AtomicLong> blockedByIp = new ConcurrentHashMap<>();

    public void recordBlocked(String api, String ip) {
        blockedByApi.computeIfAbsent(api, k -> new AtomicLong()).incrementAndGet();
        blockedByIp.computeIfAbsent(ip, k -> new AtomicLong()).incrementAndGet();
    }


}
