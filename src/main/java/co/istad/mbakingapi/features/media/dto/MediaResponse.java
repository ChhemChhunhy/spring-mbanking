package co.istad.mbakingapi.features.media.dto;

import lombok.Builder;

@Builder
public record MediaResponse(
        //unique
        String name,
        String contentType,//PNG,JPEG,MP4
        String extension,
        String uri,
        Long size
) {
}
