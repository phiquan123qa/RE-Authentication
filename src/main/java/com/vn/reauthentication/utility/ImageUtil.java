package com.vn.reauthentication.utility;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class ImageUtil {
    public static String saveImage(MultipartFile file, String url) {
        try{
            String originName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String fileName =  System.currentTimeMillis()+ originName;
            Path folderPath = Paths.get(url);
            if(!Files.exists(folderPath)){
                Files.createDirectories(folderPath);
            }
            Path imagePath = folderPath.resolve(fileName);
            Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
