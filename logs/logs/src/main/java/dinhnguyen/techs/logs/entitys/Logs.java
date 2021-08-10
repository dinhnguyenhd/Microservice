package dinhnguyen.techs.logs.entitys;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "log_service")
public class Logs {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_service")
	private Long id;

	@Column(unique = true, name = "name")
	private String name;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "logService")
	private List<LogError> errors;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LogError> getErrors() {
		return errors;
	}

	public void setErrors(List<LogError> errors) {
		this.errors = errors;
	}

}
