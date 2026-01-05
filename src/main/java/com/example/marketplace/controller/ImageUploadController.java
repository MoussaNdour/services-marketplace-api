package com.example.marketplace.controller;

import com.example.marketplace.entity.Image;
import com.example.marketplace.service.interfaces.ImageServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/uploads")
public class ImageUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    ImageServiceInterface service;

    @GetMapping("/image")
    public ResponseEntity getAllImages(){
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping(value="/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file) {
        // Save the file to the directory
        Image image = service.save(file);

        return ResponseEntity.ok(image);

    }

    @DeleteMapping("/image/{id}")
    private ResponseEntity<String> deleteImage(@PathVariable int id){
        service.deleteById(id);

        return ResponseEntity.ok("Image successfully deleted");
    }




}
