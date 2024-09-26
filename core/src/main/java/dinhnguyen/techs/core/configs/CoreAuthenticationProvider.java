package dinhnguyen.techs.core.configs;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dinhnguyen.techs.core.exceptions.InvalidUserException;
import dinhnguyen.techs.core.form.users.LoginForm;

@Component
public class CoreAuthenticationProvider implements AuthenticationProvider {

	@Resource
	CoreUserDetailsService coreUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws InvalidUserException {
		UsernamePasswordAuthenticationToken userLogin  = (UsernamePasswordAuthenticationToken) authentication;
		LoginForm request = (LoginForm) userLogin.getPrincipal();
		final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		if (StringUtils.isEmpty(username)) {
			throw new InvalidUserException("Invalid user name or password");
		}
		CoreUerDetails user = null;
		try {
			user = coreUserDetailsService.loadUserByUsername(request.getEmail());
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
				throw new InvalidUserException("Invalid user name or password");

		} catch (InvalidUserException exception) {
			throw new InvalidUserException("Invalid user name or password");
		}
		return createSuccessfulAuthentication(authentication, user);
	}

	private Authentication createSuccessfulAuthentication(final Authentication authentication,
			final CoreUerDetails user) {
		UsernamePasswordAuthenticationToken infoUser = new UsernamePasswordAuthenticationToken(user,
				authentication.getCredentials(), user.getAuthorities());
		infoUser.setDetails(authentication.getDetails());
		return infoUser;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);

	}
}