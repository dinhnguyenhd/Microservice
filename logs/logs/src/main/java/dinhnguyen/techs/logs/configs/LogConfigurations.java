package dinhnguyen.techs.logs.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import dinhnguyen.techs.commons.kafka.KafkaLogsSender;

@Configuration
public class LogConfigurations {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public KafkaLogsSender kafkaLogsSender() {
		return new KafkaLogsSender();
	}

}
