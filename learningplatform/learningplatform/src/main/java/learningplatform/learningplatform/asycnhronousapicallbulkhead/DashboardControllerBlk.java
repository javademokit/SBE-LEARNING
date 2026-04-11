package learningplatform.learningplatform.asycnhronousapicallbulkhead;

import learningplatform.learningplatform.asycnchronousapicallbywebclint.ExternalApiServiceAs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class DashboardControllerBlk {

    @Autowired
    ExternalApiServiceAs service;

    @GetMapping("/dashboard1")
    public Map<String, String> getDashboard() throws Exception {

        CompletableFuture<String> paymentFuture = service.getPayments().toFuture();
        CompletableFuture<String> orderFuture = service.getOrders().toFuture();

        CompletableFuture.allOf(paymentFuture, orderFuture).join();

        Map<String, String> response = new HashMap<>();
        response.put("payment", paymentFuture.get());
        response.put("orders", orderFuture.get());

        return response;
    }
}