package dinhnguyen.techs.mails.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class PaymentListener {
	@Autowired
	private JavaMailSender javaMailSender;

	@KafkaListener(topics = "payments", groupId = "ms-service")
	public void listen(String message) {
		System.out.println("OrderListener - group-id: " + message);
		sendMail();
	}

	public void sendMail() {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("someone@localhost");
		mailMessage.setReplyTo("someone@localhost");
		mailMessage.setFrom("someone@localhost");
		mailMessage.setSubject("Subject : How to send mail ");
		mailMessage.setText("I don't want to code ! ");
		javaMailSender.send(mailMessage);
	}

}
