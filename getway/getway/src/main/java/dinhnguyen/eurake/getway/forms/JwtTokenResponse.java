package dinhnguyen.eurake.getway.forms;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtTokenResponse {

	private String email;
	private String accessToken;
	private String refreshToken;
	private Set<String> roles;


}
