package learningplatform.learningplatform.asycnhronousapicall;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class ExternalApiServiceRT {

    private final RestTemplate restTemplate = new RestTemplate();

    @Async
    public CompletableFuture<String> getUserProfile() {
        String response = restTemplate.getForObject(
                "http://user-service/profile", String.class);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    public CompletableFuture<String> getOrders() {
        String response = restTemplate.getForObject(
                "http://order-service/orders", String.class);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    public CompletableFuture<String> getPayments() {
        String response = restTemplate.getForObject(
                "http://payment-service/payments", String.class);
        return CompletableFuture.completedFuture(response);
    }
}