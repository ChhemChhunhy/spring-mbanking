package co.istad.mbakingapi.features.media.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record MediaResponse(
        //unique
        String name,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        String contentType,//PNG,JPEG,MP4
        String extension,
        String uri,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        Long size
) {
}
