package cn.edu.szu.aicourse.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    String upload(MultipartFile file);

    String getFileUrl(String fileName);
}
