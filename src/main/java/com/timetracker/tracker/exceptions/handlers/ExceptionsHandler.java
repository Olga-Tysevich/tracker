package com.timetracker.tracker.exceptions.handlers;

import com.timetracker.tracker.dto.resp.ConstraintViolation;
import com.timetracker.tracker.dto.resp.ExceptionResponse;
import com.timetracker.tracker.dto.resp.ValidationErrorResp;
import com.timetracker.tracker.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class handles exceptions thrown in the application and provides appropriate responses based on the type of exception.
 * It includes handling for various custom exceptions as well as standard exceptions like ConstraintViolationException,
 * MethodArgumentNotValidException, and HttpMessageNotReadableException.
 */
@RestControllerAdvice
public class ExceptionsHandler {

    /**
     * Handles exceptions related to access denied for resources.
     *
     * @param e The AccessDeniedException that was thrown.
     * @return ResponseEntity containing the exception response.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessExceptions(AccessDeniedException e) {
        return buildExceptionResponse(HttpStatus.FORBIDDEN, e);
    }

    /**
     * Handles exceptions related to invalid record period.
     *
     * @param e The InvalidRecordPeriodException that was thrown.
     * @return ResponseEntity containing the exception response.
     * @see com.timetracker.tracker.exceptions.InvalidRecordPeriod
     */
    @ExceptionHandler(InvalidRecordPeriod.class)
    public ResponseEntity<?> invalidRecordPeriodExceptions(Exception e) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, e);
    }

    /**
     * Handles exceptions related to unreadable HTTP messages or invalid refresh token.
     *
     * @param e The exception that was thrown.
     * @return ResponseEntity containing the exception response.
     * @see com.timetracker.tracker.exceptions.InvalidRefreshTokenException
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> httpMessageNotReadableException(Exception e) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, e);
    }

    /**
     * Handles exceptions related to invalid user role.
     *
     * @param e The exception that was thrown.
     * @return ResponseEntity containing the exception response.
     * @see com.timetracker.tracker.exceptions.InvalidRole
     */
    @ExceptionHandler(InvalidRole.class)
    public ResponseEntity<?> notInvalidRoleExceptions(Exception e) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, e);
    }

    /**
     * Handles exceptions related to the absence of the requested object in the system.
     *
     * @param e The exception that was thrown.
     * @return ResponseEntity containing the exception response.
     * @see com.timetracker.tracker.exceptions.NotFoundException
     * @see com.timetracker.tracker.exceptions.UserNotFoundException
     */
    @ExceptionHandler({NotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<?> notFoundExceptions(Exception e) {
        return buildExceptionResponse(HttpStatus.NOT_FOUND, e);
    }

    /**
     * Handles exceptions associated with an attempt to add an object that already exists on the system.
     *
     * @param e The exception that was thrown.
     * @return ResponseEntity containing the exception response.
     * @see com.timetracker.tracker.exceptions.ObjectAlreadyExist
     * @see com.timetracker.tracker.exceptions.UserAlreadyExist
     */
    @ExceptionHandler({ObjectAlreadyExist.class, UserAlreadyExist.class})
    public ResponseEntity<?> existExceptions(Exception e) {
        return buildExceptionResponse(HttpStatus.CONFLICT, e);
    }

    /**
     * Handles exceptions related to mismatched passwords.
     *
     * @param e The exception that was thrown.
     * @return ResponseEntity containing the exception response.
     * @see com.timetracker.tracker.exceptions.PasswordMismatchException
     */
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<?> passwordMismatchExceptions(Exception e) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, e);
    }

    /**
     * Handles exceptions related to lack of authorization.
     *
     * @param e The exception that was thrown.
     * @return ResponseEntity containing the exception response.
     * @see com.timetracker.tracker.exceptions.UnauthorizedException
     */
    @ExceptionHandler({UnauthorizedException.class, InvalidRefreshTokenException.class})
    public ResponseEntity<?> unAuthExceptions(Exception e) {
        return buildExceptionResponse(HttpStatus.UNAUTHORIZED, e);
    }

    /**
     * Handles exceptions related to passing an unsupported data type.
     *
     * @param e The exception that was thrown.
     * @return ResponseEntity containing the exception response.
     * @see com.timetracker.tracker.exceptions.UnauthorizedException
     */
    @ExceptionHandler({UnsupportedDTO.class, HttpMediaTypeException.class, MissingRequestCookieException.class,
            MissingRequestHeaderException.class, MissingRequestValueException.class})
    public ResponseEntity<?> badRequestExceptions(Exception e) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, e);
    }


    /**
     * Handles MethodArgumentNotValidException by extracting field errors and creating a response with validation errors.
     *
     * @param e The exception that was thrown.
     * @return ResponseEntity containing the exception response.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationExceptions(MethodArgumentNotValidException e) {
        final List<ConstraintViolation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ConstraintViolation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorResp(violations));
    }

    /**
     * Handles ConstraintViolationException by extracting constraint violations and creating a response with validation errors.
     *
     * @param e The exception that was thrown.
     * @return ResponseEntity containing the exception response.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintValidationException(ConstraintViolationException e) {
        final List<ConstraintViolation> violations = e.getConstraintViolations().stream()
                .map(violation -> new ConstraintViolation(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorResp(violations));
    }


    /**
     * Handles InternalAuthenticationServiceException and NoResourceFoundException by returning a ResponseEntity with status code 404.
     *
     * @param e The exception that was thrown.
     * @return ResponseEntity containing the exception response.
     */
    @ExceptionHandler({InternalAuthenticationServiceException.class, NoResourceFoundException.class})
    public ResponseEntity<?> internalAuthenticationServiceException(Exception e) {
        return buildExceptionResponse(HttpStatus.NOT_FOUND, e);
    }

    /**
     * Handles any other exception by returning a ResponseEntity with status code 500.
     * If the exception is an AccessDeniedException or AuthenticationException, it is rethrown.
     *
     * @param e The exception that was thrown.
     * @return ResponseEntity containing the exception response.
     * @throws Exception If the exception is an AccessDeniedException or AuthenticationException, it is rethrown.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerError(Exception e) throws Exception {
        if (e instanceof AccessDeniedException
                || e instanceof AuthenticationException) {
            throw e;
        }
        return buildExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    /**
     * Builds the ExceptionResponse object with the provided HTTP status code and exception details.
     *
     * @param code The HTTP status code.
     * @param e    The exception that was thrown.
     * @return ResponseEntity containing the exception response.
     */
    private ResponseEntity<Object> buildExceptionResponse(HttpStatusCode code, Exception e) {
        var exceptionResponse = ExceptionResponse.builder()
                .HttpStatusCode(code.value())
                .ExceptionMessage(e.getMessage())
                .TimeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(code).body(exceptionResponse);
    }
}
