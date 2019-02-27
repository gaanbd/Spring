package com.techmango.common.exception;


public class AuthoritiesException extends RuntimeException {

    private static final long serialVersionUID = -4048879670586706994L;

    public AuthoritiesException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
