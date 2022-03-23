package br.com.example.domain.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
