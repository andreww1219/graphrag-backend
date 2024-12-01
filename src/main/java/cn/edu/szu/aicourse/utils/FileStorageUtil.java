package cn.edu.szu.aicourse.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileStorageUtil {
    
    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.max-size}")
    private Long maxFileSize;

    public String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }

        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("文件过大");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.contains(".") || !fileName.substring(fileName.lastIndexOf(".")).equals(".txt")) {
            throw new IllegalArgumentException("仅支持TXT格式文件");
        }

        try {
            // 确保上传目录存在
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 使用原始文件名
            Path filePath = Paths.get(uploadPath, fileName);
            
            // 如果文件已存在，先删除
            Files.deleteIfExists(filePath);
            
            // 保存文件
            Files.copy(file.getInputStream(), filePath);
            
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }

    public Path getFilePath(String fileName) {
        return Paths.get(uploadPath, fileName);
    }

    public void deleteFile(String fileName) {
        try {
            Files.deleteIfExists(Paths.get(uploadPath, fileName));
        } catch (IOException e) {
            throw new RuntimeException("文件删除失败", e);
        }
    }

    public List<String> getAllFiles() {
        try {
            return Files.list(Paths.get(uploadPath))
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.endsWith(".txt"))  // 只返回txt文件
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("获取文件列表失败", e);
        }
    }
} 