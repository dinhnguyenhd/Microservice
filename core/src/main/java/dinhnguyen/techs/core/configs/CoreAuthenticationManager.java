package dinhnguyen.techs.core.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class CoreAuthenticationManager implements AuthenticationManager {

	@Autowired
	CoreAuthenticationProvider coreAuthenticationProvider;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return coreAuthenticationProvider.authenticate(authentication);
	}

}
