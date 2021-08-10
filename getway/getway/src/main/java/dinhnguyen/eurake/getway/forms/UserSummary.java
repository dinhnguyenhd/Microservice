package dinhnguyen.eurake.getway.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserSummary {
	private Long id;
	private String name;
	private String email;
}