package dinhnguyen.techs.logs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dinhnguyen.techs.logs.entitys.Logs;
import dinhnguyen.techs.logs.services.LogService;

@RestController
@RequestMapping("/logs")
public class LogController {

	@Autowired
	private LogService logService;

	@GetMapping("/list/{serviceName}")
	public ResponseEntity<Logs> list(@PathVariable("serviceName") String serviceName) {
		Logs logs = this.logService.getServiceByName(serviceName);
		return new ResponseEntity<Logs>(logs, HttpStatus.OK);

	}

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return new ResponseEntity<String>("log test", HttpStatus.OK);

	}
}
