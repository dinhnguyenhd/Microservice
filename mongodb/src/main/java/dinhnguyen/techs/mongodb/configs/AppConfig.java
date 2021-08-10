package dinhnguyen.techs.mongodb.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import dinhnguyen.techs.commons.kafka.KafkaLogsSender;

@Configuration
public class AppConfig {

	public @Bean MongoClient mongoClient() {
		return MongoClients.create("mongodb://localhost:27017");
	}

	public @Bean MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), "demos");
	}

	@Bean
	public Gson gson() {
		Gson gson = new Gson();
		return gson;
	}

	@Bean
	public KafkaLogsSender kafkaSender() {
		return new KafkaLogsSender();

	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}