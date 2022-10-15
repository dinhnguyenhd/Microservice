package dinhnguyen.eurake.getway.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSummary {
	public UserSummary(Long id2, String email2, String name2) {
		this.id = id2;
		this.email = email2;
		this.name = name2;
	}

	private Long id;
	private String name;
	private String email;
}