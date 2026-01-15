package learningplatform.learningplatform.RateLimitConfig;

public class TokenBucket {

    private final int capacity;
    private final long windowMs;
    private int tokens;
    private long lastRefill;

    public TokenBucket(int capacity, long windowMs) {
        this.capacity = capacity;
        this.windowMs = windowMs;
        this.tokens = capacity;
        this.lastRefill = System.currentTimeMillis();
    }

    synchronized boolean allow() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefill;

        if (elapsed >= windowMs) {
            tokens = capacity;
            lastRefill = now;
        }

        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }
}
