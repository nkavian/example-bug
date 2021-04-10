package com.example.resource.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The enum ErrorCode.
 */
@Schema(enumAsRef = true, description = "See [ErrorCode](#section/ErrorCode-Enum) for more details.")
public enum ErrorCode {

    CONTENT_TYPE_NOT_SUPPORTED(null, "The content type specified in the header is not supported."),
    HTTP_METHOD_NOT_SUPPORTED(null, "The HTTP method used is not supported."),
    PARAMETER_REQUIRED(null, "A required parameter was missing."),
    TYPE_MISMATCH(null, "The provided parameter was of the wrong type."),
    UNEXPECTED_ERROR(null, "An unexpected error occurred."),
    UNKNOWN(null, "Unknown");

    @Data
    @Accessors(chain = true)
    public static class Error {

        @NotBlank
        @Schema(description = "The unique code of this error.", example = "CURRENCY_REQUIRED")
        private final ErrorCode code;

        @NotBlank
        @Schema(description = "The description of this error.", example = "The parameter currency is required.")
        private String message;

        @Schema(description = "An optional parameter name this error applies to.", example = "currency")
        private String parameter;

    }

    @Data
    @Accessors(chain = true)
    @Schema(name = "ErrorMessage")
    public static class ErrorPayload {

        @NotBlank
        @Schema(example = "61db578f-91b8-4729-8b8a-c11c26f0b36b", pattern = "/^([a-fA-F0-9]{8}-(?:[a-fA-F0-9]{4}-){3}[a-fA-F0-9]{12}){1}$/")
        private UUID id = UUID.randomUUID();

        private List<Error> errors;

        public ErrorPayload addError(final Error error) {
            if (errors == null) {
                errors = new LinkedList<>();
            }
            errors.add(error);
            return this;
        }

    }

    /** The constant ENUMS. */
    private static final Map<String, ErrorCode> ENUMS = new HashMap<>();

    static {
        for (final ErrorCode errorCode : ErrorCode.values()) {
            ENUMS.put(errorCode.name(), errorCode);
        }
    }

    /**
     * Retrieve an enum for the name.
     *
     * @param name the name
     * @return the enum
     */
    @JsonCreator
    public static ErrorCode forName(final String name) {
        return ENUMS.getOrDefault(name, UNKNOWN);
    }

    /**
     * To list.
     *
     * @return the list of enums
     */
    public static List<ErrorCode> toList() {
        return toList(true);
    }

    /**
     * To list.
     *
     * @param withoutUnknown flag to remove the UNKNOWN element if desired
     * @return the list of enums
     */
    public static List<ErrorCode> toList(final boolean withoutUnknown) {
        final List<ErrorCode> list = new LinkedList<>(ENUMS.values());
        if (withoutUnknown) {
            list.remove(UNKNOWN);
        }
        return list;
    }

    /** The message. */
    private final String message;

    /** The parameter. */
    private final String parameter;

    /**
     * Instantiates a new error code enum.
     *
     * @param parameter the parameter
     * @param message   the message
     */
    ErrorCode(final String parameter, final String message) {
        assert name().length() <= 64;
        this.parameter = parameter;
        this.message = message;
    }

    /**
     * Gets the error.
     *
     * @return the error
     */
    public Error getError() {
        return new Error(this).setParameter(parameter).setMessage(message);
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the parameter.
     *
     * @return the parameter
     */
    public String getParameter() {
        return parameter;
    }

    @Converter(autoApply = true)
    public static class JPAConverter implements AttributeConverter<ErrorCode, String> {

        @Override
        public String convertToDatabaseColumn(final ErrorCode errorCode) {
            return errorCode == null ? null : errorCode.name();
        }

        @Override
        public ErrorCode convertToEntityAttribute(final String name) {
            return name == null ? null : ErrorCode.forName(name);
        }

    }

    @Component
    public static class SpringConverter implements org.springframework.core.convert.converter.Converter<String, ErrorCode> {

      @Override
      public ErrorCode convert(final String name) {
          return name == null ? null : ErrorCode.forName(name);
      }

    }

}
