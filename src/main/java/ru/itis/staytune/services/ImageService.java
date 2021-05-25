package ru.itis.staytune.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    public String saveImage(MultipartFile image) throws IOException {
        String name = UUID.randomUUID().toString() + image.getOriginalFilename();
        File convertFile = new File("src/main/resources/photos/" + name);
        FileOutputStream fileStream = new FileOutputStream(convertFile);
        fileStream.write(image.getBytes());
        fileStream.close();
        return name;
    }
}
