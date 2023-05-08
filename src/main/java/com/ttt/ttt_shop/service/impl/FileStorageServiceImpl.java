package com.ttt.ttt_shop.service.impl;


import com.ttt.ttt_shop.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path root;
    @Autowired
    public FileStorageServiceImpl(ServletContext servletContext){
        try {
            // folder for product
            String path = servletContext.getRealPath("/WEB-INF/static/img/products");
            root = Paths.get(path);
            if(!Files.exists(root))
                Files.createDirectories(root);

        }catch (IOException ioException){
            throw new RuntimeException("Cannot initialize storage" + ioException);
        }
    }

    @Override
    public String save(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String randomName = UUID.randomUUID().toString() + extension;
            try (InputStream inputStream = file.getInputStream();
                 OutputStream outputStream = Files.newOutputStream(this.root.resolve(randomName))) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            return randomName;
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public boolean delete(String filename) {
        try {
            Path file = root.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
