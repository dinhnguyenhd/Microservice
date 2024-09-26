package dinhnguyen.techs.commons.forms;

public class LogErrors {

	private String serviceName;
	private String className;
	private String jsonInput;
	private String errors;

	public LogErrors(String serviceName, String className, String jsonInput, String errors) {
		super();
		this.serviceName = serviceName;
		this.className = className;
		this.jsonInput = jsonInput;
		this.errors = errors;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getJsonInput() {
		return jsonInput;
	}

	public void setJsonInput(String jsonInput) {
		this.jsonInput = jsonInput;
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

}
