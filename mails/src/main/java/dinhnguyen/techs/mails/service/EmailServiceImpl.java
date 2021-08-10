package dinhnguyen.techs.mails.service;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	public SimpleMailMessage template;

	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("noreply@baeldung.com");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);
			FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
			helper.addAttachment("Invoice", file);
			emailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendSimpleMessage(String to, String subject, String text) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@baeldung.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);

	}

}
