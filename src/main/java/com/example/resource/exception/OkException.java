package com.example.resource.exception;

import lombok.Getter;

/**
 * The class OkException.
 */
public final class OkException extends RuntimeException {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Getter
    private final String body;

    public OkException() {
        body = null;
    }

    public OkException(final String body) {
        this.body = body;
    }

}
