package learningplatform.learningplatform.asycnhronousapicall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class AggregatorController {

    @Autowired
    ExternalApiServiceRT service;

    @GetMapping("/dashboard")
    public Map<String, String> getDashboard() throws Exception {

        CompletableFuture<String> profileFuture = service.getUserProfile();
        CompletableFuture<String> ordersFuture = service.getOrders();
        CompletableFuture<String> paymentsFuture = service.getPayments();

        // Wait until all APIs finish
        CompletableFuture.allOf(profileFuture, ordersFuture, paymentsFuture).join();

        Map<String, String> result = new HashMap<>();
        result.put("profile", profileFuture.get());
        result.put("orders", ordersFuture.get());
        result.put("payments", paymentsFuture.get());

        return result;
    }
}