package learningplatform.learningplatform.UserService;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

@Service
public class SmsService {

    // ✅ Replace these with your Twilio credentials
    private final String ACCOUNT_SID = "AC4e351e192e38fd2326483f9cff002422";
    private final String AUTH_TOKEN = "efab993b6fe462ba95d5feaa962305fd";
    private final String FROM_NUMBER = "8095042235"; // Your Twilio phone number

    public SmsService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public String sendSms(String toNumber, String messageBody) {
        try {
            Message message = Message.creator(
                    new PhoneNumber(toNumber), // To
                    new PhoneNumber(FROM_NUMBER), // From
                    messageBody
            ).create();

            return "SMS sent successfully! SID: " + message.getSid();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send SMS: " + e.getMessage();
        }
    }

    public String sendSmsFast2Sms(String toNumber, String messageBody) {
        try {
            String apiKey = "YOUR_FAST2SMS_API_KEY";
            String url = "https://www.fast2sms.com/dev/bulkV2?authorization=" + apiKey
                    + "&route=q&message=" + URLEncoder.encode(messageBody, "UTF-8")
                    + "&language=english&flash=0&numbers=" + toNumber;

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            return "Fast2SMS response: " + response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send SMS: " + e.getMessage();
        }
    }

}
