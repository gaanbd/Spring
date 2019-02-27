package com.techmango.common.exception;

import java.io.Serializable;

public class ErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private int errorCode;
    private String errorStatus;
    
    public ErrorVM(String message) {
        this(message, 0 ,null);
    }

    public ErrorVM(String message, int errorCode, String errorStatus) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorStatus = errorStatus;
    }

    public String getMessage() {
        return message;
    }

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorStatus() {
		return errorStatus;
	}
	
}
