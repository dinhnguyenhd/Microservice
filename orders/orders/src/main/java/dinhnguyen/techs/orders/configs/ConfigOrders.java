package dinhnguyen.techs.orders.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import dinhnguyen.techs.commons.kafka.KafkaLogsSender;

@Configuration
public class ConfigOrders {

	@Bean
	Gson gson() {
		Gson gson = new Gson();
		return gson;
	}

	@Bean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	KafkaLogsSender kafkaSender() {
		return new KafkaLogsSender();

	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
