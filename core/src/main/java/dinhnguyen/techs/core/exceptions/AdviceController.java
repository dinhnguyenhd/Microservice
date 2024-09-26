package dinhnguyen.techs.core.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class AdviceController {

	@ExceptionHandler(value = { BadCredentialsException.class })
	public ResponseEntity<ErrorMessage> BadCredentialsException(BadCredentialsException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN, "BadCredentialsException", new Date(),
				ex.getMessage());
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { InvalidUserException.class, AuthenticationException.class })
	public ResponseEntity<ErrorMessage> InvalidUserException(InvalidUserException ex) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, "InvalidUserException", new Date(),
				ex.getMessage());
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorMessage> AccessDeniedException(Exception ex) {
		ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN, "AccessDeniedException" + ex.getMessage(),
				new Date(), ex.getMessage());
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(StorageException.class)
	public ResponseEntity<ErrorMessage> StorageException(Exception ex) {
		ErrorMessage message = new ErrorMessage(HttpStatus.INSUFFICIENT_STORAGE, "StorageException", new Date(),
				ex.getMessage());
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> GeneraleException(Exception ex) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, "Exception", new Date(), ex.getMessage());
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExpireJwtException.class)
	public ResponseEntity<ErrorMessage> ExpiredJwtException(ExpireJwtException ex) {
		ErrorMessage message = new ErrorMessage(HttpStatus.UNAUTHORIZED, "ExpireJwtException", new Date(),
				ex.getMessage());
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Map<String, String>> ValidationException(ValidationException ex) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<Map<String, String>>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
