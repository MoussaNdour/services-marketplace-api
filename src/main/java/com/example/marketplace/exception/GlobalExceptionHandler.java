package com.example.marketplace.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(EmailAlreadyUserException.class)
    public ResponseEntity<Map<String,String>> handleEmailException(EmailAlreadyUserException e){
        Map<String,String> error=new HashMap<>();
        error.put("error",e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<Map<String,String>> handleForbiddenOperationException(ForbiddenOperationException e){
        Map<String,String> error=new HashMap<>();
        error.put("error",e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ProviderNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProviderNotFoundException(ProviderNotFoundException e){
        Map<String,String> error=new HashMap<>();
        error.put("error",e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleServiceNotFoundException(ServiceNotFoundException e){
        Map<String,String> error=new HashMap<>();
        error.put("error",e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
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
}
