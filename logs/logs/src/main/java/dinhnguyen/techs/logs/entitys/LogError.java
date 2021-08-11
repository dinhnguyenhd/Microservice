package dinhnguyen.techs.logs.entitys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "logs_error")
public class LogError {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_logs")
	private Long id;

	@Column(name = "class_name")
	private String className;

	@Column(name = "json_input")
	private String jsonInput;

	@Column(name = "message")
	private String message;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_service", nullable = false)
	@JsonBackReference
	private Logs logService;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Logs getLogService() {
		return logService;
	}

	public void setLogService(Logs logService) {
		this.logService = logService;
	}

}
