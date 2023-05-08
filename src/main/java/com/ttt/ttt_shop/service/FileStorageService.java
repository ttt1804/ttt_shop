package com.ttt.ttt_shop.service;



import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {

    public String save(MultipartFile file);

    public boolean delete(String filename);


}
