package dinhnguyen.techs.commons.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import dinhnguyen.techs.commons.exceptions.ErrorResponse;
import dinhnguyen.techs.commons.exceptions.RecordNotFoundExceptions;

@RestControllerAdvice
public class CommonsAdvice {

	Logger logger = LoggerFactory.getLogger(CommonsAdvice.class);

	@ExceptionHandler(RecordNotFoundExceptions.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse notFoundHandler(RecordNotFoundExceptions notFoundException) {
		logger.info(notFoundException.getMessage());
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), notFoundException.getMessage());
	}

	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse httpClientErrorHandler(HttpClientErrorException httpClientErrorException) {
		logger.info(httpClientErrorException.getMessage());
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), httpClientErrorException.getMessage());
	}

}
