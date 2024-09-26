package dinhnguyen.techs.logs.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dinhnguyen.techs.commons.forms.LogErrors;
import dinhnguyen.techs.logs.services.LogService;

@Service
public class LogListeners {

	@Autowired
	private LogService logService;

	@Autowired
	private ObjectMapper objectMapper;

	@KafkaListener(topics = { "logs" }, groupId = "ms-microservice")
	public void received(String message) {
		System.out.println(" message " + message);
		try {
			LogErrors log = this.objectMapper.readValue(message, LogErrors.class);
			this.logService.save(log);

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

}
