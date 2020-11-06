package br.com.clinica.controller.exceptions;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.clinica.constantes.MensagensUtil;
import br.com.clinica.service.exception.NegocioException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@Bean
	public ErrorAttributes errorAttributes() {
		// Hide exception field in the return object
		return new DefaultErrorAttributes() {
			@Override
			public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
				Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
				errorAttributes.remove("exception");
				return errorAttributes;
			}
		};
	}

	@ExceptionHandler(AutheException.class)
	public ResponseEntity<StandardError> handleCustomException(HttpServletResponse res, AutheException ex)
			throws IOException {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(err.getStatus()).body(err);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandardError> handleAccessDeniedException(HttpServletResponse res, AccessDeniedException ex)
			throws IOException {
		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), MensagensUtil.ACESSO_NEGADO,
				System.currentTimeMillis());
		return ResponseEntity.status(err.getStatus()).body(err);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardError> handleException(HttpServletResponse res, Exception ex) throws IOException {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), MensagensUtil.ERRO,
				System.currentTimeMillis());
		return ResponseEntity.status(err.getStatus()).body(err);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<StandardError> objectNotFound(NegocioException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validacao",
				System.currentTimeMillis());

		for (ObjectError x : e.getBindingResult().getAllErrors()) {
			err.addError(x.getObjectName(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
