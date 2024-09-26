package dinhnguyen.techs.core.exceptions;

public class ExpireJwtException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExpireJwtException(String message) {
		super(message);
	}

}
