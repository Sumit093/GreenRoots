package com.ecoscholars.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Value("${cloudinary.folder:ecoscholars}")
    private String cloudinaryFolder;

    public Map uploadFile(MultipartFile file) {
        try {
            Map params = ObjectUtils.asMap(
                    "folder", cloudinaryFolder,
                    "resource_type", "auto"
            );
            return cloudinary.uploader().upload(file.getBytes(), params);
        } catch (IOException e) {
            log.error("Cloudinary upload failed: {}", e.getMessage());
            throw new RuntimeException("Image upload failed", e);
        }
    }

    public Map deleteFile(String publicId) {
        try {
            return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            log.error("Cloudinary delete failed for publicId {}: {}", publicId, e.getMessage());
            throw new RuntimeException("Image deletion failed", e);
        }
    }
}
