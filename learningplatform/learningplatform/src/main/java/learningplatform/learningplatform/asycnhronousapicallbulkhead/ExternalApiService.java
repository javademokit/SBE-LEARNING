package learningplatform.learningplatform.asycnhronousapicallbulkhead;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Service
public class ExternalApiService {

    @Autowired
    private WebClient webClient;

    // PAYMENT SERVICE BULKHEAD
    @Bulkhead(name = "paymentService", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "paymentFallback")
    public CompletableFuture<String> getPayment() {

        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/1")
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();
    }

    // ORDER SERVICE BULKHEAD
    @Bulkhead(name = "orderService", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "orderFallback")
    public CompletableFuture<String> getOrders() {

        return webClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/2")
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();
    }

    // FALLBACK METHODS
    public CompletableFuture<String> paymentFallback(Throwable t) {
        return CompletableFuture.completedFuture("Payment Service Busy");
    }

    public CompletableFuture<String> orderFallback(Throwable t) {
        return CompletableFuture.completedFuture("Order Service Busy");
    }
}
