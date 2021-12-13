package iftm.pedro.aproject.services.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public ServiceException(String msg, HttpStatus status){
        this.message = msg;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return status;
    }
}
