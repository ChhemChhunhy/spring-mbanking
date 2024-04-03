package co.istad.mbakingapi.features.media;

import co.istad.mbakingapi.base.BaseReset;
import co.istad.mbakingapi.features.media.dto.MediaResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-single")
    MediaResponse uploadSingle(@RequestPart MultipartFile file){
        return mediaService.uploadSingle(file,"IMAGE");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-multiple")
    List<MediaResponse> uploadMultiple(@RequestPart List<MultipartFile> files){
        return mediaService.uploadMultiple(files,"IMAGE");
    }
    @GetMapping("/{mediaName}")
    MediaResponse loadMediaByName(@PathVariable String mediaName){
        return mediaService.loadMediaByName(mediaName,"IMAGE");
    }

    @DeleteMapping("/{mediaName}")
    MediaResponse deleteMediaByName(@PathVariable String mediaName){
        return mediaService.deleteMediaByName(mediaName,"IMAGE");
    }
    @GetMapping
    List<MediaResponse> loadAllMedias(){
        return mediaService.loadAllMedias("IMAGE");
    }

    @GetMapping("/download/{mediaName}")
    public ResponseEntity<?> downloadFile(@PathVariable String mediaName, HttpServletRequest request){
        return mediaService.serverFile(mediaName,"IMAGE", request);
    }

//    @GetMapping("/download/{name}")
//    public MediaResponse downloadFile(@PathVariable("name") String name, HttpServletResponse response) {
//        return mediaService.downloadMediaByName(name,"IMAGE",response);
//    }

//    @GetMapping("/download/{name}")
//    public BaseReset<?> downloadFile(@PathVariable("name") String name, HttpServletResponse response) {
//        var resultFiles = mediaService.downloadMediaByName(name,"IMAGE",response);
//        return BaseReset.builder()
//                .status(true)
//                .code(HttpStatus.OK.value())
//                .message("Download successfully")
//                .timestamp(LocalDateTime.now())
//                .data(resultFiles)
//                .build();
   // }

// Assuming this is a controller or a service method where you want to retrieve all media files


}
