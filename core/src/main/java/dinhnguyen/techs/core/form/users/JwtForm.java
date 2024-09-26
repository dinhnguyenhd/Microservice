package dinhnguyen.techs.core.form.users;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtForm {

	private String email;
	private String refreshToken;
	private String accessToken;

}
