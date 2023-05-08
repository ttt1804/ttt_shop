package com.ttt.ttt_shop.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileToStringConverter implements Converter<MultipartFile, String> {

    @Override
    public String convert(MultipartFile file) {
        return file.getOriginalFilename();
    }

}
