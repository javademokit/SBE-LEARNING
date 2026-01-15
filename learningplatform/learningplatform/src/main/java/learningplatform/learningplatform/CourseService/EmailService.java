package learningplatform.learningplatform.CourseService;

import learningplatform.learningplatform.model.Subscriber;
import learningplatform.learningplatform.repository.SubscriberRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final SubscriberRepository subscriberRepo;

    public EmailService(JavaMailSender mailSender, SubscriberRepository subscriberRepo) {
        this.mailSender = mailSender;
        this.subscriberRepo = subscriberRepo;
    }

    public void sendNewsToAll(String subject, String messageBody) {
        List<Subscriber> subscribers = subscriberRepo.findAll();
        for (Subscriber sub : subscribers) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(sub.getEmail());
            msg.setSubject(subject);
            msg.setText(messageBody);
            mailSender.send(msg);
        }
    }
}

