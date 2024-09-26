package dinhnguyen.techs.core.form.users;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {

	@NotEmpty(message = "Tell me your email")
	private String email;

	@NotEmpty(message = "Tell me your passsword ")
	@Size(min = 6, max = 100, message = "Your password is too short or too long")
	private String password;

}