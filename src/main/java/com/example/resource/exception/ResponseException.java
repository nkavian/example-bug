package com.example.resource.exception;

import org.springframework.http.ResponseEntity;

import lombok.Data;

/**
 * The class ResponseException.
 */
@Data
public class ResponseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final ResponseEntity<Object> response;

}