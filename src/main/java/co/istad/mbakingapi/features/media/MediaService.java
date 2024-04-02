package co.istad.mbakingapi.features.media;

import co.istad.mbakingapi.features.media.dto.MediaResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    MediaResponse uploadSingle(MultipartFile file,String folderName);

}
