package co.istad.mbakingapi.features.media;

import co.istad.mbakingapi.features.media.dto.MediaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService{
    @Value("${media.server-path}")
    private String serverPath;
    @Value("${media.base-uri}")
    private String baseUri;
    @Override
    public MediaResponse uploadSingle(MultipartFile file,String folderName) {
        //log.info("New name : {}",newName);
        //log.info("Extension: {}", extension);
        //generate new unique name for file upload
        String newName = UUID.randomUUID().toString();
        int lastDotIndex = file.getOriginalFilename().lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(lastDotIndex+1);
        newName=newName + "." +extension;
        //copy file to server
        Path path = Paths.get(serverPath + folderName +"\\"+newName);
        try {
            Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);

        }catch (IOException e){
            throw  new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage()
            );
        }

        return MediaResponse.builder()
                .name(newName)
                .contentType(file.getContentType())
                .extension(extension)
                .size(file.getSize())
                .uri(String.format("%s%s/%s",baseUri,folderName,newName))
                .build();


//        //extract extension from file upload
//        //Assume profile.png
//        //String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//
//        //generate new unique name for file upload
//        String newFileName = newName + "." + extension;
//
//        //save file to disk
//        try {
//            file.transferTo(new File("C:\\Users\\<NAME>\\Desktop\\" + newFileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //return new MediaResponse(newName, newFileName);
//        return null;
//
//        //UUID uuid = UUID.randomUUID();
//        //String fileName = uuid.toString() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//        //file.transferTo(new File("C:\\Users\\<NAME>\\Desktop\\" + fileName));
//       // log.info("upload ",file.getContentType());
        //MediaResoponse.builder().name(newName)
        //String.format("%s%s/%s",baseUri,folerName,newName)
        //.
    }
}
