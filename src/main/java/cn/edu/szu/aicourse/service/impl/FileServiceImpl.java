package cn.edu.szu.aicourse.service.impl;

import cn.edu.szu.aicourse.service.FileService;
import cn.edu.szu.aicourse.utils.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    
    private final FileStorageUtil fileStorageUtil;

    @Override
    public String saveFile(MultipartFile file) {
        return fileStorageUtil.saveFile(file);
    }

    @Override
    public Path getFilePath(String fileName) {
        return fileStorageUtil.getFilePath(fileName);
    }

    @Override
    public void deleteFile(String fileName) {
        fileStorageUtil.deleteFile(fileName);
    }

    @Override
    public List<String> getAllFiles() {
        return fileStorageUtil.getAllFiles();
    }
} 