package dinhnguyen.techs.core.service;

import org.springframework.data.domain.Page;

import dinhnguyen.techs.core.entity.User;
import dinhnguyen.techs.core.form.users.JwtToken;
import dinhnguyen.techs.core.form.users.LoginForm;
import dinhnguyen.techs.core.form.users.RegisterForm;
import dinhnguyen.techs.core.form.users.SearchForm;
import dinhnguyen.techs.core.form.users.JwtForm;

public interface UserService {

	public dinhnguyen.techs.core.form.users.UserForm register(RegisterForm form);

	public JwtToken login(LoginForm user);

	public JwtToken refreshToken(JwtToken oldToken);

	
	public void signout(JwtForm token);

	public Page<User> search(SearchForm form);
	

}
