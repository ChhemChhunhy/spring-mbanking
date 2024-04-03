package co.istad.mbakingapi.base;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BaseReset<T>(
        Boolean status,
        Integer code,
        String message,
        LocalDateTime timestamp,
        T data
) {
}
