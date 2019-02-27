package com.techmango.common.exception;


public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 5281364421866367094L;

    private int errorCode;
    
    private String status;
    
    public BusinessException(String exceptionMessage) {
        super(exceptionMessage);
    }
    
    public BusinessException(String exceptionMessage, int errorCode, String status) {
        super(exceptionMessage);
        this.errorCode=errorCode;
        this.status=status;
    }

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
