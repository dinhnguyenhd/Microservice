package dinhnguyen.techs.mails.configs;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import dinhnguyen.techs.commons.kafka.KafkaLogsSender;

@Configuration
public class MailConfigurations {

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", false);
		mailProperties.put("mail.smtp.starttls.enable", false);
		mailSender.setJavaMailProperties(mailProperties);
		mailSender.setHost("localhost");
		mailSender.setPort(25);
		mailSender.setProtocol("smtp");
		mailSender.setUsername("dinhnguyen");
		mailSender.setPassword("");
		return mailSender;
	}

	@Bean
	public SimpleMailMessage templateSimpleMessage() {
		SimpleMailMessage simmpleMail = new SimpleMailMessage();
		return simmpleMail;
	}

	@Bean
	public KafkaLogsSender kafkaSender() {
		return new KafkaLogsSender();

	}

}
