package co.istad.mbakingapi.exception;

import co.istad.mbakingapi.base.BaseError;
import co.istad.mbakingapi.base.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ValidationException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    BaseErrorResponse handleValidationError(MethodArgumentNotValidException ex){
        BaseError<List<?>> baseError = new BaseError<>();
        List<Map<String,Object>> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    Map<String,Object> error =new HashMap<>();
                    error.put("field",fieldError.getField());
                    error.put("message",fieldError.getDefaultMessage());
                    errors.add(error);
                });
        baseError.setCode(HttpStatus.BAD_GATEWAY.getReasonPhrase());
        baseError.setDescription(errors);
        //return Map.of("errors",errors);
        return new BaseErrorResponse(baseError);
    }
}
