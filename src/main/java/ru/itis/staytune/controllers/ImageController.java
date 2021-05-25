package ru.itis.staytune.controllers;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    @GetMapping("/image/{photoName}")
    public ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable(value="photoName") String photoName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        InputStream in = new FileInputStream(new File("src/main/resources/photos/" + photoName));
        byte[] media = in.readAllBytes();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(media, headers, HttpStatus.OK);
    }
}
