package dinhnguyen.techs.mails.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import dinhnguyen.techs.commons.kafka.KafkaLogsSender;
import dinhnguyen.techs.mails.forms.OrderForm;
import dinhnguyen.techs.mails.forms.ProductForm;

@Service
public class OrderListener {

	@Autowired
	private KafkaLogsSender kafkaLogsSender;

	@Autowired
	private JavaMailSender javaMailSender;

	@KafkaListener(topics = "orders", groupId = "ms-service")
	public void listen(String message) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			OrderForm order = objectMapper.readValue(message, OrderForm.class);
			System.out.println(" Namr " + order.getTitle() + " Email " + order.getEmail());
			for (ProductForm form : order.getProducts()) {
				System.out.println(" Product Name " + form.getName());
			}
			sendMail();
		} catch (Exception e) {
			String json = this.kafkaLogsSender.convert("mail-service", "OrderListener", null, e.getMessage());
			kafkaLogsSender.send("logs", json);
		}

	}

	public void sendMail() {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("someone@localhost");
		mailMessage.setReplyTo("someone@localhost");
		mailMessage.setFrom("someone@localhost");
		mailMessage.setSubject("Subject : Send details order to your email ");
		mailMessage.setText("I want to attach excel file but i don't have time ");
		javaMailSender.send(mailMessage);

	}
}
