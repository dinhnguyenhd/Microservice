package dinhnguyen.techs.logs.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dinhnguyen.techs.commons.forms.LogErrors;
import dinhnguyen.techs.logs.entitys.LogError;
import dinhnguyen.techs.logs.entitys.Logs;
import dinhnguyen.techs.logs.repositorys.LogRepository;

@Service
public class LogServiceImp implements LogService {

	@Autowired
	private LogRepository logRepository;

	@Override
	public Logs getServiceByName(String name) {
		return this.logRepository.getServiceByName(name);
	}

	@Override
	public List<Logs> list() {
		return this.logRepository.findAll();
	}

	@Override
	public Logs save(LogErrors error) {
		Logs logs = new Logs();
		logs.setName(error.getServiceName());
		LogError dblog = new LogError();
		dblog.setClassName(error.getClassName());
		dblog.setJsonInput(error.getJsonInput());
		dblog.setMessage(error.getErrors());
		// check service name already exits in DB:
		Logs already = this.logRepository.getServiceByName(error.getServiceName().trim());
		// Case 1: log not exits in DB:
		if (already == null) {
			List<LogError> list = Arrays.asList(dblog);
			logs.setErrors(list);
			dblog.setLogService(logs);
			logs = this.logRepository.save(logs);
		}
		// Case 2: Log already exit in DB:
		else {
			logs.getErrors().add(dblog);
			dblog.setLogService(logs);
			logs = this.logRepository.save(logs);
		}
		return logs;
	}

	@Override
	public Logs listErrorOfSV(String serviceName) {
		Logs logs = this.getServiceByName(serviceName);
		List<LogError> list = logs.getErrors();
		logs.setErrors(list);
		return logs;
	}

}
