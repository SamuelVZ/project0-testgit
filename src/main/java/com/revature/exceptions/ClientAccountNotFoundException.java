package com.revature.exceptions;

public class ClientAccountNotFoundException extends Exception{
    public ClientAccountNotFoundException() {
    }

    public ClientAccountNotFoundException(String message) {
        super(message);
    }

    public ClientAccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientAccountNotFoundException(Throwable cause) {
        super(cause);
    }

    public ClientAccountNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
