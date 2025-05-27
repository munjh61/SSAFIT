package com.ssafy.ssafit.model.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileUploadUtil {
    private static final String UPLOAD_DIR = "C:/Users/SSAFY/JYW/SSAFIT_Final_Project/SSAFIT/front/public/images"; // 경로 조정 가능
//    private static final String UPLOAD_DIR = "C:/SSAFY/final_project/SSAFIT/front/public/images"; // 경로 조정 가능

    public String save(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR, fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }
}
