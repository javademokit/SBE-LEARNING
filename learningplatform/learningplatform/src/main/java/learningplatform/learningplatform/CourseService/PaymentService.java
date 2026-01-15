package learningplatform.learningplatform.CourseService;


import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    public String createOrder(double amount, String courseName) throws Exception {
        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", (int)(amount * 100)); // amount in paise
        options.put("currency", "INR");
        options.put("receipt", "receipt_" + System.currentTimeMillis());
        options.put("notes", new JSONObject().put("course", courseName));

        Order order = client.orders.create(options);
        return order.toString();
    }
}
