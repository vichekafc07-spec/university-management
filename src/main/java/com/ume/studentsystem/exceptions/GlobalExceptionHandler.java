package com.ume.studentsystem.exceptions;

import com.ume.studentsystem.util.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // handle 404 resource not found in server
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<?>> handleNotFoundException(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(APIResponse.error(ex.getMessage(),HttpStatus.NOT_FOUND));
    }

    // handle validation DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        // get errors key
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((item)->{
            errors.put(item.getField(),item.getDefaultMessage());
        });
        Map<String,Object> res = new HashMap<>();
        res.put("success",false);
        res.put("code",HttpStatus.BAD_REQUEST.value()); // 400
        res.put("message","Validation Failed");
        res.put("data",null);
        res.put("errors",errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    // bad request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIResponse<?>> handleBadRequestException(BadRequestException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.error(ex.getMessage(),HttpStatus.BAD_REQUEST));
    }

    // duplicate exceptions
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<APIResponse<?>> handleDuplicateException(DuplicateResourceException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(APIResponse.error(ex.getMessage(),HttpStatus.CONFLICT));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse<?>> handleEnumError(HttpMessageNotReadableException ex) {
        String message = "Invalid value. Allowed values of Enum";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.error(message,HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<?>> handleException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIResponse.error("Something Wrong!", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    // Handle Spring Security 401 Unauthorized
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIResponse<?>> handleAuthenticationException(org.springframework.security.core.AuthenticationException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(APIResponse.error(ex.getMessage() != null ? ex.getMessage() : "Authentication required",
                        HttpStatus.UNAUTHORIZED));
    }

    // Handle Spring Security 403 Forbidden
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse<?>> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(APIResponse.error(ex.getMessage() != null ? ex.getMessage() : "You do not have permission to access this resource",
                        HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<APIResponse<Object>> handleMissingParam(
            MissingServletRequestParameterException ex
    ) {
        return ResponseEntity.badRequest()
                .body(APIResponse.error(ex.getMessage(),
                        HttpStatus.BAD_REQUEST));
    }
}
