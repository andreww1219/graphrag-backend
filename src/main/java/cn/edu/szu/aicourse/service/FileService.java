package cn.edu.szu.aicourse.service;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.List;

public interface FileService {
    String saveFile(MultipartFile file);
    Path getFilePath(String fileName);
    void deleteFile(String fileName);
    List<String> getAllFiles();
} 