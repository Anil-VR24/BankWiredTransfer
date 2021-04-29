package com.anil.banktansferwired.exception;

import com.anil.banktansferwired.util.ApplicationConstants;

public class AccountServiceException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public AccountServiceException() {
        super(ApplicationConstants.INSUFFICIENT_FUNDS);
    }

    public AccountServiceException(String message) {
        super(message);
    }

    public AccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountServiceException(Throwable cause) {
        super(cause);
    }

    public AccountServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
	
}
