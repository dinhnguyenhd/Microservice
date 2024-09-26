package dinhnguyen.techs.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dinhnguyen.techs.core.form.users.JwtToken;
import dinhnguyen.techs.core.security.JwtTokenProvider;
import dinhnguyen.techs.core.service.UserService;

@RestController
public class JwtController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/authen/refresh-token")
	public ResponseEntity<JwtToken> refreshToken(@RequestBody JwtToken oldToken, HttpServletRequest request) {
		JwtToken newToken = this.userService.refreshToken(oldToken);
		return new ResponseEntity<JwtToken>(newToken, HttpStatus.OK);

	}

	@PostMapping("/authen/jwt-expired/{accessToken}")
	public ResponseEntity<Boolean> isExpiredToken(@PathVariable("accessToken") String accessToken) {
		boolean flag = this.jwtTokenProvider.isExpired(accessToken);
		return new ResponseEntity<Boolean>(flag, HttpStatus.OK);

	}

}
