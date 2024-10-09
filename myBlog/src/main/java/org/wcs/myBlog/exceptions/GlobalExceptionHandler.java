package org.wcs.myBlog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) //404.NOT_FOUND
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class) // 403.FORBIDDEN Access
    public ResponseEntity<String> handleForbiddenAccess(ResourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(ResourceNotFoundException.class) /=
    public ResponseEntity<String> handleInternalServerError(ResourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ResourceNotFoundException.class) // 502.BAD_GATEWAY
    public ResponseEntity<String> handleBadGateway(ResourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
    }


    @ExceptionHandler(ResourceNotFoundException.class) // 503.SERVICE_UNAVAILABLE
    public ResponseEntity<String> handleServiceUnavailable(ResourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ResourceNotFoundException.class) // 504.GATEWAY_TIMEOUT
    public ResponseEntity<String> handleGatewayTimeOut(ResourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(ResourceNotFoundException.class) // 401.UNAUTHORIZED - Authentification nécéssaire
    public ResponseEntity<String> handleUnauthorized(ResourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class) // 400.BAD_REQUEST
    public ResponseEntity<String> handleBadRequest(ResourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class) // 410.GONE
    public ResponseEntity<String> handleGone(ResourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.GONE);
    }

    @ExceptionHandler(Exception.class) // 500.INTERNAL_SERVER_ERROR
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue");
    }
}
