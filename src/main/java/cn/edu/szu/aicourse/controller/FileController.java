package cn.edu.szu.aicourse.controller;

import cn.edu.szu.aicourse.common.result.Result;
import cn.edu.szu.aicourse.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public Result uploadFile(@RequestBody MultipartFile file) {
        String fileName = fileService.saveFile(file);
        return Result.success(fileName, "文件上传成功");
    }

    @DeleteMapping("/{fileName}")
    public Result deleteFile(@PathVariable String fileName) {
        fileService.deleteFile(fileName);
        return Result.success("文件删除成功");
    }

    @GetMapping("/list")
    public Result getAllFiles() {
        List<String> files = fileService.getAllFiles();
        return Result.success(files, "获取文件列表成功");
    }
}
