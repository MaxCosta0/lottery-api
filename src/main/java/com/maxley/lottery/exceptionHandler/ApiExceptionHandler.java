package com.maxley.lottery.exceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.maxley.lottery.exception.DomainException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<Object> DomainExceptionHandler(DomainException ex, WebRequest request){
		
		var status = HttpStatus.NOT_FOUND;
		ResponseError error = new ResponseError();
		error.setStatus(status.value());
		error.setTitle(ex.getMessage());
		error.setDateTime(LocalDateTime.now());
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var fields = new ArrayList<Field>();
		
		for(ObjectError err: ex.getBindingResult().getAllErrors()) {
			String name = ((FieldError) err).getField();
			String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
			
			fields.add(new Field(name, message));
		}
		
		ResponseError error = new ResponseError();
		error.setStatus(status.value());
		error.setTitle("Invalid Field");
		error.setDateTime(LocalDateTime.now());
		error.setFields(fields);
		
		return super.handleExceptionInternal(ex, error, headers, status, request);
	}
}