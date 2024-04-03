package co.istad.mbakingapi.features.media;

import co.istad.mbakingapi.features.media.dto.MediaResponse;
import co.istad.mbakingapi.util.MediaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
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
//        int lastDotIndex = file.getOriginalFilename().lastIndexOf(".");
//        String extension = file.getOriginalFilename().substring(lastDotIndex+1);
        String extension = MediaUtil.extractExtension(file.getOriginalFilename());
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

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files, String folderName) {
//        List<MediaResponse> mediaResponseList = List.of();
//        for (MultipartFile file : files) {
//            MediaResponse mediaResponse = this.uploadSingle(file,folderName);
//            mediaResponseList.add(mediaResponse);
//        }
//        return mediaResponseList;
        //create empty array list , wait for adding uploaded file
        List<MediaResponse> mediaResponses = new ArrayList<>();
        files.forEach(file -> {
            MediaResponse mediaResponse = this.uploadSingle(file,folderName);
            mediaResponses.add(mediaResponse);
        });

        return mediaResponses;
    }

    @Override
    public MediaResponse loadMediaByName(String mediaName,String folderName) {
        //create absolute path of media
        Path path = Paths.get(serverPath + folderName +"\\"+mediaName);
        try {
            Resource resource =new UrlResource(path.toUri());
            if (!resource.exists()){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Media not found"
                );
            }
//            URLConnection connection = resource.getURL().openConnection();
//            String contentType = connection.getContentType();
            return MediaResponse.builder()
                    .name(mediaName)
                    .contentType(Files.probeContentType(path))
                    .extension(MediaUtil.extractExtension(mediaName))
                    .size(resource.contentLength())
                    .uri(String.format("%s%s/%s",baseUri,folderName,mediaName))
                    .build();

        }catch (MalformedURLException e){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                   e.getLocalizedMessage()
            );
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage()
            );
        }


    }

    @Override
    public MediaResponse deleteMediaByName(String mediaName, String folderName) {
        Path path = Paths.get(serverPath + folderName +"\\"+mediaName);
        try {
            long size = Files.size(path);
            if ( Files.deleteIfExists(path)){
                return MediaResponse.builder()
                        .name(mediaName)
                        .contentType(Files.probeContentType(path))
                        .extension(MediaUtil.extractExtension(mediaName))
                        .size(size)
                        .uri(String.format("%s%s/%s",baseUri,folderName,mediaName))
                        .build();


            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Media has not found"
            );
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                  String.format("Media path %s cannot be deleted",e.getLocalizedMessage())
            );
        }

    }
}
