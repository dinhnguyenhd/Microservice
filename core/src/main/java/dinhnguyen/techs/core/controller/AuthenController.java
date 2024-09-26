package dinhnguyen.techs.core.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dinhnguyen.techs.core.exceptions.SystemException;
import dinhnguyen.techs.core.exceptions.ValidationException;
import dinhnguyen.techs.core.form.users.JwtForm;
import dinhnguyen.techs.core.form.users.JwtToken;
import dinhnguyen.techs.core.form.users.LoginForm;
import dinhnguyen.techs.core.form.users.RegisterForm;
import dinhnguyen.techs.core.form.users.UserForm;
import dinhnguyen.techs.core.service.UserService;

@RestController
public class AuthenController {

	@Autowired
	private UserService userService;


	@PostMapping(value = "/authen/signup")
	public ResponseEntity<UserForm> signup(@RequestBody RegisterForm user) {
		UserForm userRp = null;
		try {
			userRp = this.userService.register(user);
		} catch (Exception e) {
			new SystemException("System shutdown,try it again in the future ! ");
		}
		return new ResponseEntity<UserForm>(userRp, HttpStatus.OK);

	}

	@PostMapping("/authen/login")
	public ResponseEntity<JwtToken> login(@Valid @RequestBody LoginForm user, BindingResult bindingResult)
			throws ValidationException {
		JwtToken userRp = null;
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult);
		} else {
			userRp = this.userService.login(user);
		}
		return new ResponseEntity<JwtToken>(userRp, HttpStatus.OK);

	}

	@PostMapping("/authen/logout")
	public void signout(@RequestBody JwtForm simple) {
		try {
			this.userService.signout(simple);
			SecurityContextHolder.clearContext();
		} catch (Exception e) {
			new SystemException("System shutdown,try it again in the future ! ");
		}

	}

}
