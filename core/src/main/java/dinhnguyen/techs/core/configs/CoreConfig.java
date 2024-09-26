package dinhnguyen.techs.core.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class CoreConfig {

	@Bean
	ModelMapper getModelMappler() {
		return new ModelMapper();
	}

	@Bean
	ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
}
