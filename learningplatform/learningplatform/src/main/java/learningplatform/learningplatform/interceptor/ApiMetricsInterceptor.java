package learningplatform.learningplatform.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import learningplatform.learningplatform.RateLimitConfig.InMemoryRateLimiter;
import learningplatform.learningplatform.RateLimitConfig.RateLimitMetricsStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Component
public class ApiMetricsInterceptor implements HandlerInterceptor {

    private final ApiMetricsStore store;
    private static final String START_TIME = "startTime";

    @Autowired
    private InMemoryRateLimiter limiter;
    @Autowired
    private RateLimitMetricsStore rateLimitMetrics;
    public ApiMetricsInterceptor(ApiMetricsStore store) {
        this.store = store;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {


        String api= request.getRequestURI();
        String clientIp = getClientIp(request);

        // ================== RATE LIMIT ADD (NEW) ==================
        if (!limiter.allow(clientIp, api)) {
            rateLimitMetrics.recordBlocked(api, clientIp);
            response.setStatus(429);
            response.setHeader("Retry-After", "1");
            response.getWriter().write("Rate limit exceeded");
            return false; // ⛔ stop request
        }

        request.setAttribute(START_TIME, System.currentTimeMillis());
        store.requestCount
                .computeIfAbsent(api, k -> new AtomicLong())
                .incrementAndGet();
        store.clientIpStats
                .computeIfAbsent(api, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(clientIp, k -> new AtomicLong())
                .incrementAndGet();

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        String api = request.getRequestURI();
        long duration =
                System.currentTimeMillis() - (long) request.getAttribute(START_TIME);

        store.totalTime
                .computeIfAbsent(api, k -> new AtomicLong())
                .addAndGet(duration);

        store.maxTime
                .computeIfAbsent(api, k -> new AtomicLong())
                .updateAndGet(prev -> Math.max(prev, duration));
    }

    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isEmpty()) {
            return xff.split(",")[0].trim();
        }

        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isEmpty()) {
            return realIp;
        }

        String ip = request.getRemoteAddr();
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}

