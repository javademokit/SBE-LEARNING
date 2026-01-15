package learningplatform.learningplatform.AuthController;


import learningplatform.learningplatform.UserService.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;
    // Example: POST /api/sms/send
    @PostMapping("/send")
    public String sendSms(
            @RequestParam String to,
            @RequestParam String message
    ) {
        return smsService.sendSms(to, message);
    }
}
