package com.example.marketplace.service.implementations;

import com.example.marketplace.entity.Image;
import com.example.marketplace.exception.ImageManipulationException;
import com.example.marketplace.exception.NonexistingImageException;
import com.example.marketplace.repository.ImageRepository;
import com.example.marketplace.service.interfaces.ImageServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Service
public class ImageService implements ImageServiceInterface {

    @Autowired
    ImageRepository repository;

    @Value("${file.upload-dir}")
    String uploadDir;



    @Override
    public List<Image> getAll() {
        return repository.findAll();
    }

    @Override
    public Image getById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Image save(MultipartFile file)  {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                //Put a log here later
                throw new ImageManipulationException("Unknown problem with the folder of images");
            }
        }

        String fileName = file.getOriginalFilename();

        Path filePath = uploadPath.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            //Put a log here later
            throw new ImageManipulationException("Unknown problem with the copy operation on the folder of images");
        }

        String path="/images/" + fileName;

        Image image=new Image();
        image.setPath(path);
        image.setName(fileName);

        return repository.save(image);
    }

    @Override
    public void deleteById(int id) {
        Image image=getById(id);

        if(image!=null){
            String filename = Paths.get(image.getPath()).getFileName().toString();

            Path imagePath = Paths.get(uploadDir).resolve(filename);

            //delete of the image from the folder
            try{
                Files.deleteIfExists(imagePath);
                repository.deleteById(id);
            }
            catch (IOException e){
                System.out.println("Error occurred by trying to delete this image, maybe the image doesn't exist");
            }
        }
        else
            throw new NonexistingImageException("The image you're trying to delete doesn't exist");

    }
}
