package learningplatform.learningplatform.interceptor;


import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ApiMetricsStore {
    public final Map<String, AtomicLong> requestCount = new ConcurrentHashMap<>();
    public final Map<String, AtomicLong> totalTime = new ConcurrentHashMap<>();
    public final Map<String, AtomicLong> maxTime = new ConcurrentHashMap<>();
    public final Map<String, Map<String, AtomicLong>> clientIpStats =
            new ConcurrentHashMap<>();
}


