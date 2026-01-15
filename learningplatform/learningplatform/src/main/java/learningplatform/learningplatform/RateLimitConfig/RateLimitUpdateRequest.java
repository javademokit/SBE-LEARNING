package learningplatform.learningplatform.RateLimitConfig;

public class RateLimitUpdateRequest {
    private String api;
    private int requests;
    private long windowMs;

    // getters and setters
    public String getApi() { return api; }
    public void setApi(String api) { this.api = api; }
    public int getRequests() { return requests; }
    public void setRequests(int requests) { this.requests = requests; }
    public long getWindowMs() { return windowMs; }
    public void setWindowMs(long windowMs) { this.windowMs = windowMs; }
}
