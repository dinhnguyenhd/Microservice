package dinhnguyen.techs.core.form.users;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtToken {

	private String email;
	private String accessToken;
	private String refreshToken;
	private Set<String> roles;
	private boolean revoked;
	private boolean expired;
	@Override
	public String toString() {
		return "JwtToken [email=" + email + ", accessToken=" + accessToken + ", refreshToken=" + refreshToken
				+ ", roles=" + roles + "]";
	}
	

}
