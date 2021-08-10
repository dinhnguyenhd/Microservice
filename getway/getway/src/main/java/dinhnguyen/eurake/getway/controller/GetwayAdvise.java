package dinhnguyen.eurake.getway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;

import dinhnguyen.eurake.getway.exceptions.BadRequestException;
import dinhnguyen.eurake.getway.exceptions.ConflictException;
import dinhnguyen.eurake.getway.forms.ErrorResponse;
import dinhnguyen.eurake.getway.securitys.JwtAuthenticationFilter;

@RestControllerAdvice
public class GetwayAdvise {
	Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@ExceptionHandler({ BadRequestException.class, NoSuchFieldException.class, NumberFormatException.class,
			JsonProcessingException.class, IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse runtime(RuntimeException exception) {
		logger.info(exception.getMessage());
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
	}

	@ExceptionHandler(ConflictException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse conflictHandler(ConflictException conflictException) {
		logger.info(conflictException.getMessage());
		return new ErrorResponse(HttpStatus.CONFLICT.value(), conflictException.getMessage());
	}

}
