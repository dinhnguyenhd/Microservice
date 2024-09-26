package dinhnguyen.techs.core.form.users;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterForm {

	@Size(min = 1, max = 15, message = "Your name not empty, tell me your first name")
	private String name;

	@Email
	@Size(min = 3, max = 100, message = "Tell me your email correctly")
	private String email;

	@Size(min = 6, max = 50, message = "Tell me your password correctly")
	private String password;

	private List<String> role;

}
