package co.istad.mbakingapi.features.media;

import co.istad.mbakingapi.features.media.dto.MediaResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    MediaResponse uploadSingle(MultipartFile file,String folderName);
    List<MediaResponse> uploadMultiple(List<MultipartFile> files,String folderName);
    MediaResponse loadMediaByName(String mediaName,String folderName);

    MediaResponse deleteMediaByName(String mediaName,String folderName);

}
