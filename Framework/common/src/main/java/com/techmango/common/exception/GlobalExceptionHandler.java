package com.techmango.common.exception;


import java.nio.file.AccessDeniedException;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	private MessageSourceAccessor accessor;

	public GlobalExceptionHandler(MessageSource messageSource) {
		accessor = new MessageSourceAccessor(messageSource);
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorVM processException(BusinessException exc) {
		return new ErrorVM(populateErrorVM(exc));
		//return new ErrorVM(populateErrorVM(exc), exc.getErrorCode(), exc.getStatus());//customization
	}
	
	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorVM processException(ApplicationException exc) {
		return new ErrorVM(populateErrorVM(exc));
	}
	
	@ExceptionHandler(AuthoritiesException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorVM processException(AuthoritiesException exc) {
		return new ErrorVM(populateErrorVM(exc));
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ErrorVM processException(HttpRequestMethodNotSupportedException exc) {
		return new ErrorVM(populateErrorVM(exc));
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorVM processAccessDeniedxception(AccessDeniedException exc) {
		return new ErrorVM(populateErrorVM(exc));
	}

	private String populateErrorVM(Exception ex) {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/i18n/messages");
		accessor = new MessageSourceAccessor(messageSource,Locale.ENGLISH);
		return accessor.getMessage(ex.getLocalizedMessage());
	}
	
}