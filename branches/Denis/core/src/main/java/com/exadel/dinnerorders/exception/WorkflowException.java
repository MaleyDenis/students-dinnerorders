package com.exadel.dinnerorders.exception;

/**
 * @author Alex Okunevich
 */
public class WorkflowException extends RuntimeException {
    public WorkflowException() {
    }

    public WorkflowException(Throwable cause) {
        super(cause);
    }

    public WorkflowException(String message) {
        super(message);
    }

    public WorkflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
