package org.wcs.myBlog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RessourceNotFoundException.class) //404.NOT_FOUND
    public ResponseEntity<String> handleResourceNotFound(RessourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenAccessException.class) // 403.FORBIDDEN Access
    public ResponseEntity<String> handleForbiddenAccess(ForbiddenAccessException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadGatewayException.class) // 502.BAD_GATEWAY
    public ResponseEntity<String> handleBadGateway(BadGatewayException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ServiceUnavailableException.class) // 503.SERVICE_UNAVAILABLE
    public ResponseEntity<String> handleServiceUnavailable(ServiceUnavailableException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(GatewayTimeOutException.class) // 504.GATEWAY_TIMEOUT
    public ResponseEntity<String> handleGatewayTimeOut(GatewayTimeOutException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(UnauthorizedException.class) // 401.UNAUTHORIZED - Authentification nécéssaire
    public ResponseEntity<String> handleUnauthorized(UnauthorizedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class) // 400.BAD_REQUEST
    public ResponseEntity<String> handleBadRequest(BadRequestException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GoneException.class) // 410.GONE
    public ResponseEntity<String> handleGone(GoneException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.GONE);
    }
//Gestion des erreurs hors definitions
    @ExceptionHandler(Exception.class) // 500.INTERNAL_SERVER_ERROR
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue");
    }
}
