package learningplatform.learningplatform.AuthController;


import learningplatform.learningplatform.CourseService.CourseService;
import learningplatform.learningplatform.CourseService.PaymentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.json.JSONObject;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/create-order/{courseId}")
    public ResponseEntity<?> createOrder(@PathVariable String courseId) {
        try {
            // Get course price and name from your DB
            var courseOpt = courseService.getCourseById(courseId);
            if (courseOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid course ID");
            }
            var course = courseOpt.get();

            String order = paymentService.createOrder(course.getPrice(), course.getTitle());
            return ResponseEntity.ok(new JSONObject(order).toMap());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Payment order failed: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody String payload, Authentication auth) {
        try {
            JSONObject json = new JSONObject(payload);
            String razorpayOrderId = json.getString("razorpay_order_id");
            String razorpayPaymentId = json.getString("razorpay_payment_id");
            String razorpaySignature = json.getString("razorpay_signature");
            String courseId = json.getString("courseId");

            // ✅ Verify signature
            String generatedSignature = String.valueOf(com.razorpay.Utils.verifyPaymentSignature(json, "YOUR_KEY_SECRET"));
            if (generatedSignature == null) {
                return ResponseEntity.badRequest().body("Payment verification failed!");
            }

            // ✅ Add course to user
            courseService.buyCourse(auth.getName(), courseId);

            return ResponseEntity.ok("Payment verified and course purchased!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Verification failed: " + e.getMessage());
        }
    }
}
