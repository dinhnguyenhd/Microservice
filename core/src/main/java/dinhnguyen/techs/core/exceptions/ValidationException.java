package dinhnguyen.techs.core.exceptions;

import org.springframework.validation.BindingResult;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidationException extends RuntimeException {

	private String message;
	private static final long serialVersionUID = 1L;
	private BindingResult bindingResult;

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

	public ValidationException(String message, BindingResult bindingResult) {
		super();
		this.message = message;
		this.bindingResult = bindingResult;
	}

	
}
