package co.istad.mbakingapi.features.media;

import co.istad.mbakingapi.features.media.dto.MediaResponse;
import co.istad.mbakingapi.util.MediaUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

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


@Override
public MediaResponse downloadMediaByName(String mediaName, String folderName, HttpServletResponse response) {
    Path path = Paths.get(serverPath + folderName + "//" + mediaName);
    File file = path.toFile();

    if (file.exists()) {
        try {
            byte[] imageData = Files.readAllBytes(path); // Read file content into byte array
            String extension = MediaUtil.extractExtension(mediaName);
            response.setContentType(MediaUtil.getContentType(extension));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + mediaName + "\"");
            response.getOutputStream().write(imageData);

            return MediaResponse.builder()
                    .name(file.getName())
                    .uri(baseUri + file.getName())
                    .extension(MediaUtil.extractExtension(mediaName))
                    .size(file.length())
                    .build();
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error reading file: " + e.getMessage()
            );
        }
    } else {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("File '%s' not found!", mediaName)
        );
    }
}

@Override
public List<MediaResponse> loadAllMedias(String folderName) {
    Path path = Paths.get(serverPath + folderName);

    try {
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Media not found"
            );
        }

        List<MediaResponse> mediaResponseList = new ArrayList<>();
        // Iterate over files in the directory
        try (Stream<Path> paths = Files.list(path)) {
            paths.forEach(filePath -> {

                try {
                    String fileName = filePath.getFileName().toString();
                    // Create a MediaResponse object for each file
                    MediaResponse mediaResponse = MediaResponse.builder()
                            .name(fileName)
                            .uri(baseUri + folderName + "/" + fileName) // Ensure correct URI
                            .extension(MediaUtil.extractExtension(fileName))
                            .size(Files.size(filePath))
                            .build();
                    // Add MediaResponse to the list
                    mediaResponseList.add(mediaResponse);
                } catch (IOException e) {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Error occurred while processing media file: " + filePath.getFileName()
                    );
                }
            });
        }
        return mediaResponseList;
    } catch (IOException e) {
        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error occurred while accessing media directory"
        );
    }
}


@Override
public ResponseEntity<Resource> serverFile(String fileName, String folderName, HttpServletRequest request) {
    try {
        // Get path of the image
        Path imagePath = Paths.get(serverPath + folderName + "/" + fileName);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(Files.probeContentType(imagePath)));
            // Set Content-Disposition to "attachment" to prompt download
            headers.setContentDispositionFormData("attachment", resource.getFilename());

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(resource);
        } else {
            // Return 404 Not Found if resource does not exist or is not readable
            return ResponseEntity.notFound().build();
        }
    } catch (IOException ex) {
        // Handle exception
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

    @Override
    public Resource downloadMediaByName(String mediaName, String folderName) {
        // Create absolute path of media
        Path path = Paths.get(serverPath + folderName + "\\" + mediaName);
        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Media has not been found!");
        }
    }
    @Override
    public MediaResponse deleteByName(String mediaName, String folderName) {

        // Create absolute path of media
        Path path = Paths.get(serverPath + folderName + "\\" + mediaName);

        try {
            long size = Files.size(path);
            if (Files.deleteIfExists(path)) {
                return MediaResponse.builder()
                        .name(mediaName)
                        .contentType(Files.probeContentType(path))
                        .extension(MediaUtil.extractExtension(mediaName))
                        .size(size)
                        .uri(String.format("%s%s/%s", baseUri, folderName, mediaName))
                        .build();
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Media has not been found");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Media path %s cannot be deleted!", e.getLocalizedMessage()));
        }
    }


//    @Override
//
//    public ResponseEntity<Resource> serverFile(String fileName, String folderName, HttpServletRequest request) {
//        try {
//            // Get path of the image
//            Path imagePath = Paths.get(serverPath+folderName+"/"+fileName);
//            Resource resource = new UrlResource(imagePath.toUri());
//
//            if (resource.exists() && resource.isReadable()) {
//                return ResponseEntity
//                        .ok()
//                        .contentType(MediaType.parseMediaType(Files.probeContentType(imagePath)))
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                        .body(resource);
//            } else {
//                // Return 404 Not Found if resource does not exist or is not readable
//                return ResponseEntity.notFound().build();
//            }
//        } catch (IOException ex) {
//            // Handle exception
//            ex.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

//    @Override
//    public List<MediaResponse> loadAllMedias(String folderName) {
//        Path path = Paths.get(serverPath+folderName);
//        try {
//            Resource resource = new UrlResource(path.toUri());
//            if (!resource.exists()) {
//                throw new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        "Media not found"
//                );
//            }
//
//            List<MediaResponse> mediaResponseList = new ArrayList<>();
//            // Iterate over files in the directory
//            Files.walk(path)
//                    .filter(Files::isRegularFile) // Filter out directories
//                    .forEach(filePath -> {
//                        String fileName = filePath.getFileName().toString();
//                        try {
//                            // Create a MediaResponse object for each file
//                            MediaResponse mediaResponse = MediaResponse.builder()
//                                    .name(fileName)
//                                    .uri(baseUri + fileName)
//                                    .extension(MediaUtil.extractExtension(fileName))
//                                    .size(Files.size(filePath))
//                                    .build();
//                            // Add MediaResponse to the list
//                            mediaResponseList.add(mediaResponse);
//                        } catch (IOException e) {
//                            throw new ResponseStatusException(
//                                    HttpStatus.INTERNAL_SERVER_ERROR,
//                                    "Error occurred while processing media file: " + fileName
//                            );
//                        }
//                    });
//
//            return mediaResponseList;
//        } catch (IOException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.INTERNAL_SERVER_ERROR,
//                    "Error occurred while accessing media directory"
//            );
//        }
//    }


}
