package dinhnguyen.techs.commons.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class CommonConfigs {

	@Bean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
