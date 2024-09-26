package dinhnguyen.techs.core.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidUserException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public InvalidUserException(String message) {
		super(message);
	}

}
