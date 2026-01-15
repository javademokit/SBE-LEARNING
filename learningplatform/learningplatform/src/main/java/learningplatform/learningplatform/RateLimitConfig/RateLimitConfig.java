package learningplatform.learningplatform.RateLimitConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitConfig {
    private int requests;
    private long windowMs;
}
