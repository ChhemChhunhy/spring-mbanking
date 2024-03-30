package co.istad.mbakingapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ServiceException {
    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handlerServiceError(ResponseStatusException ex){
        return ResponseEntity.status(ex.getStatusCode())
                .body(Map.of("error", Objects.requireNonNull(ex.getReason())));
    }

}

