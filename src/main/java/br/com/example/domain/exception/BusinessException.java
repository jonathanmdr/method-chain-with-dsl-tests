package br.com.example.domain.exception;

public class BusinessException extends RuntimeException {

    private static final boolean ENABLE_SUPPRESSION = Boolean.TRUE;
    private static final boolean ENABLE_WRITABLE_STACK_TRACE = Boolean.FALSE;

    public BusinessException(final String message, final Throwable throwable) {
        super(message, throwable, ENABLE_SUPPRESSION, ENABLE_WRITABLE_STACK_TRACE);
    }

}
