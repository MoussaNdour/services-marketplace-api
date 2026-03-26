package com.example.marketplace.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<String> handleEmailException(EmailAlreadyExistException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<String> handleForbiddenOperationException(ForbiddenOperationException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(ProviderNotFoundException.class)
    public ResponseEntity<String> handleProviderNotFoundException(ProviderNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<String> handleServiceNotFoundException(ServiceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NonexistingImageException.class)
    public ResponseEntity<String> handleNonexistingImageException(NonexistingImageException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ImageManipulationException.class)
    public ResponseEntity<String> handleImageManipulationException(ImageManipulationException e)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(AskingServiceNotFoundException.class)
    public ResponseEntity<String> handleServiceAskingNotFoundException(AskingServiceNotFoundException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }


    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> handleClientNotFoundException(ClientNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ServiceProposalNotFoundException.class)
    public ResponseEntity<String> handleServiceProposalNotFoundException(ServiceProposalNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundExceptiono(CategoryNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<String> handleCategoryAlreadyExistException(CategoryAlreadyExistException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthException(AuthenticationException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password incorrect");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedUserRoleException.class)
    public ResponseEntity<String> handleUnauthorizedUserRoleException(UnauthorizedUserRoleException e)
    {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<String> handleRefeshTokenException(RefreshTokenException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(ServiceAlreadyExistingException.class)
    public ResponseEntity<String> handleServiceAlreadyExistException(ServiceAlreadyExistingException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
