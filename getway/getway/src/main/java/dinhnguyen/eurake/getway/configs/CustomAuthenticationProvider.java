package dinhnguyen.eurake.getway.configs;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dinhnguyen.eurake.getway.forms.LoginRequest;



/**
 * <p>
 * A custom Authentication provider example. To create custom
 * AuthenticationProvider, we need to implement the AuthenticationProvider
 * provide the implementation for the authenticate and support method.
 * </p>
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Resource
	UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken hic = (UsernamePasswordAuthenticationToken) authentication;
		LoginRequest request = (LoginRequest) hic.getPrincipal();
		final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		if (StringUtils.isEmpty(username)) {
			throw new BadCredentialsException("invalid login details");
		}
		UserDetails user = null;

		try {
			user = userDetailsService.loadUserByUsername(request.getEmail());
			if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword()))
				throw new BadCredentialsException("invalid login details");

		} catch (UsernameNotFoundException exception) {
			throw new BadCredentialsException("invalid login details");
		}
		return createSuccessfulAuthentication(authentication, user);
	}

	private Authentication createSuccessfulAuthentication(final Authentication authentication, final UserDetails user) {
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