package dinhnguyen.techs.logs.services;

import java.util.List;

import dinhnguyen.techs.commons.forms.LogErrors;
import dinhnguyen.techs.logs.entitys.Logs;

public interface LogService {

	public Logs getServiceByName(String name);

	public List<Logs> list();

	public Logs save(LogErrors logs);

	public Logs listErrorOfSV(String serviceName);

}
