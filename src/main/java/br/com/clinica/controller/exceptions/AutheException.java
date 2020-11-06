package br.com.clinica.controller.exceptions;

import org.springframework.http.HttpStatus;

public class AutheException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;
	private HttpStatus httpStatus;

	public AutheException(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public AutheException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
