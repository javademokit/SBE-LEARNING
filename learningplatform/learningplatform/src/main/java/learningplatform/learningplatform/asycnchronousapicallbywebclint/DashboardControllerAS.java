package learningplatform.learningplatform.asycnchronousapicallbywebclint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DashboardControllerAS {

    @Autowired
    ExternalApiServiceAs service;

    @GetMapping("/dashboard2")
    public Mono<Map<String, String>> getDashboard() {

        return Mono.zip(
                service.getUserProfile(),
                service.getOrders(),
                service.getPayments()
        ).map(tuple -> {
            Map<String, String> result = new HashMap<>();
            result.put("profile", (String) tuple.getT1());
            result.put("orders", tuple.getT2());
            result.put("payments", tuple.getT3());
            return result;
        });
    }
}