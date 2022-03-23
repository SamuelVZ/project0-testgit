package com.revature.exceptions;

public class ClientAccountFoundException extends Exception{
    public ClientAccountFoundException() {
    }

    public ClientAccountFoundException(String message) {
        super(message);
    }

    public ClientAccountFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientAccountFoundException(Throwable cause) {
        super(cause);
    }

    public ClientAccountFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
