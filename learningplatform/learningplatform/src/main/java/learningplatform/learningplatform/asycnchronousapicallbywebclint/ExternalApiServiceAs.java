package learningplatform.learningplatform.asycnchronousapicallbywebclint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiServiceAs {

    @Autowired
    private WebClient webClient;

    public Mono<?> getUserProfile() {
        return webClient.get()
                .uri("http://user-service/profile")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getOrders() {
        return webClient.get()
                .uri("http://order-service/orders")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getPayments() {
        return webClient.get()
                .uri("http://payment-service/payments")
                .retrieve()
                .bodyToMono(String.class);
    }


}