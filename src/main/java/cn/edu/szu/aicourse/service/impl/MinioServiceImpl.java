package cn.edu.szu.aicourse.service.impl;

import cn.edu.szu.aicourse.config.MinioConfig;
import cn.edu.szu.aicourse.service.MinioService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@Service
public class MinioServiceImpl implements MinioService {
    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    public String upload(MultipartFile file){
        try{
            if (file == null || file.isEmpty()){
                throw new IllegalArgumentException("文件不能为空");
            }

            if (file.getSize() > minioConfig.getFileSizeMax()){
                throw new IllegalArgumentException("文件过大");
            }

            String fileName = file.getOriginalFilename();
            log.debug("上传新文件: " + fileName);
            // TODO txt拦截
            if (fileName == null || !fileName.contains(".") || !fileName.substring(fileName.lastIndexOf(".")).equals(".txt")) {
                throw new IllegalArgumentException("仅支持TXT格式文件");
            }

            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(fileName)
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build();

            minioClient.putObject(args);

            return getFileUrl(fileName);
        }
        catch (IllegalArgumentException e) {
            // 直接抛出参数异常
            throw e;
        }
        catch (Exception e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败", e);
        }
    }

    public String getFileUrl(String fileName){
        try {
            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(minioConfig.getBucket())
                        .object(fileName)
                        .method(Method.GET)
                        .expiry(7, TimeUnit.DAYS)
                        .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("获取文件URL失败", e);
        }
    }
}
