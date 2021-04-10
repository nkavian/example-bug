package com.example.resource.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.resource.exception.BadRequestException;
import com.example.resource.exception.ForbiddenException;
import com.example.resource.exception.NotFoundException;
import com.example.resource.exception.OkException;
import com.example.resource.exception.RedirectException;
import com.example.resource.exception.ResponseException;
import com.example.resource.exception.UnathorizedException;
import com.example.resource.model.ErrorCode;
import com.example.resource.model.ErrorCode.ErrorPayload;

import io.swagger.v3.oas.annotations.Hidden;

@ControllerAdvice
public class CustomExceptionController {

    /*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorPayload> exception(final MethodArgumentNotValidException exception) {
        final List<Error> errors = new LinkedList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
        for (FieldError fieldError : fieldErrors) {
            errors.add(ErrorCode.UNKNOWN.getError().setMessage(fieldError.getField() + ", " + fieldError.getDefaultMessage()));
        }
        for (ObjectError objectError : globalErrors) {
            errors.add(ErrorCode.UNKNOWN.getError().setMessage(objectError.getObjectName() + ", " + objectError.getDefaultMessage()));
        }
        //Object result=ex.getBindingResult();//instead of above can also pass the more detailed bindingResult

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorPayload().setErrors(errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorPayload> exception(final ConstraintViolationException exception) {
        final List<Error> errors = new LinkedList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            System.out.println("-----");
            System.out.println(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
            //System.out.println(violation.getInvalidValue());
            //System.out.println(violation.getExecutableReturnValue());
            //System.out.println(violation.getLeafBean());
            //System.out.println(violation.getMessageTemplate());
            System.out.println(violation.getConstraintDescriptor().getAnnotation());
            System.out.println(violation.getPropertyPath());
            //System.out.println(violation);
            errors.add(ErrorCode.UNKNOWN.getError().setMessage(violation.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorPayload().setErrors(errors));
    }
    */

    // HTTP 200
    @Hidden
    @ExceptionHandler(value = OkException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> exception(final OkException exception) {
        return ResponseEntity.status(HttpStatus.OK).body(exception.getBody());
    }

    // HTTP 303
    @Hidden
    @ExceptionHandler(value = RedirectException.class)
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void exception(final RedirectException exception, final HttpServletRequest request, final HttpServletResponse response) {
        response.setStatus(HttpStatus.SEE_OTHER.value());
        response.setHeader("location", exception.getLocation());
        if (exception.getData() != null) {
            request.getSession().setAttribute("data", exception.getData());
        }
        try {
            response.getWriter().write("");
            response.getWriter().flush();
            response.getWriter().close();
        } catch (final IOException e) {
            //Log.error(e);
        }
    }

    // HTTP 400
    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorPayload> exception(final BadRequestException exception) {
        if (exception.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getErrors());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // HTTP 400
    @Hidden
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorPayload> exception(final HttpMediaTypeNotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorPayload().addError(ErrorCode.CONTENT_TYPE_NOT_SUPPORTED.getError()));
    }

    // HTTP 400
    @Hidden
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object exception(final HttpServletRequest request, final HttpRequestMethodNotSupportedException exception) {
        if (request.getRequestURI().toString().startsWith(request.getContextPath() + "/v1/")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorPayload().addError(ErrorCode.HTTP_METHOD_NOT_SUPPORTED.getError()));
        } else {
            return "pages/error/general-error";
        }
    }

    // HTTP 400
    @Hidden
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object exception(final HttpServletRequest request, final MethodArgumentTypeMismatchException exception) {
        //Log.info(exception);
        if (request.getRequestURI().toString().startsWith(request.getContextPath() + "/v1/")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorPayload().addError(ErrorCode.TYPE_MISMATCH.getError().setParameter(exception.getName())));
        } else {
            return "pages/error/general-error";
        }
    }

    // HTTP 400
    @Hidden
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object exception(final HttpServletRequest request, final Model model, final MissingServletRequestParameterException exception) {
        //Log.info(exception);
        if (request.getRequestURI().toString().startsWith(request.getContextPath() + "/v1/")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorPayload().addError(ErrorCode.PARAMETER_REQUIRED.getError().setParameter(exception.getParameterName())));
        } else {
            model.addAttribute("parameter", exception.getParameterName());
            return "pages/error/bad-request-error";
        }
    }

    // HTTP 400
    @Hidden
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object exception(final HttpServletRequest request, final HttpMessageNotReadableException exception) {
        //Log.info(exception);
        if (request.getRequestURI().toString().startsWith(request.getContextPath() + "/v1/")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorPayload().addError(ErrorCode.UNEXPECTED_ERROR.getError().setMessage(exception.getMessage())));
        } else {
            return "pages/error/general-error";
        }
    }

    // HTTP 401
    @Hidden
    @ExceptionHandler(value = UnathorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Void> exception(final UnathorizedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // HTTP 403
    @Hidden
    @ExceptionHandler(value = ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Void> exception(final ForbiddenException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // HTTP 404
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorPayload> exception(final NotFoundException exception) {
        if (exception.hasErrors()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getErrors());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // HTTP 500
    @Hidden
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exception(final HttpServletRequest request, final Exception exception) {
        //Log.error(exception);
        if (request.getRequestURI().toString().startsWith(request.getContextPath() + "/v1/")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else {
            return "pages/error/internal-server-error";
        }
    }

    @Hidden
    @ExceptionHandler(value = ResponseException.class)
    public ResponseEntity<Object> exception(final ResponseException exception) {
        return exception.getResponse();
    }

}