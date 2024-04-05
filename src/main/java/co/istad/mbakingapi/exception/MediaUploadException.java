package co.istad.mbakingapi.exception;

import co.istad.mbakingapi.base.BaseError;
import co.istad.mbakingapi.base.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Slf4j
public class MediaUploadException {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSize;
    //error:[]
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    BaseErrorResponse handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e){
        BaseError<String> baseError = BaseError.<String>builder()
                .code(HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase())
                .description("Media upload size maximum "+maxSize)
                .build();
        return new BaseErrorResponse(baseError);
    }
}
