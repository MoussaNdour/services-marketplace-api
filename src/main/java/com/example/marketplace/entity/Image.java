package com.example.marketplace.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Image {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "path")
    private String path;

    @Column(name = "name")
    private String name;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Resource getImage(){

        try {
            Path filePath = Paths.get(uploadDir).resolve(name);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
