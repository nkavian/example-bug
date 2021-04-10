package com.example.resource.exception;

import java.util.Arrays;
import java.util.List;

import com.example.resource.model.ErrorCode;
import com.example.resource.model.ErrorCode.Error;
import com.example.resource.model.ErrorCode.ErrorPayload;

/**
 * The class NotFoundException.
 */
public final class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private List<Error> errors;

    public NotFoundException() {
        errors = null;
    }

    public NotFoundException(final Error error) {
        errors = Arrays.asList(error);
    }

    public NotFoundException(final ErrorCode errorCode) {
        errors = Arrays.asList(errorCode.getError());
    }

    public NotFoundException(final List<Error> errors) {
        this.errors = errors;
    }

    public ErrorPayload getErrors() {
        return hasErrors() ? new ErrorPayload().setErrors(errors) : null;
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

}
