package dinhnguyen.techs.commons.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dinhnguyen.techs.commons.forms.LogErrors;

@Service
public class KafkaLogsSender {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String topic, String message) {
		this.kafkaTemplate.send(topic, message);

	}

	public String convert(String serviceName, String className, String jsonInput, String errors) {

		LogErrors logs = new LogErrors(serviceName, className, jsonInput, errors);
		String json = null;
		try {
			json = this.objectMapper.writeValueAsString(logs);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;

	}

}
