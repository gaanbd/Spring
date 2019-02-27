package com.techmango.common.exception;


public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = -7042645303986355155L;

    public ApplicationException(String exceptionMessage) {
        super(exceptionMessage);
    }

}
