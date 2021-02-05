package com.drobot.module3.exception;

public class ClientException extends Exception {

    public ClientException() {
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }
}
