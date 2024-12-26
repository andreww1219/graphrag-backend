package cn.edu.szu.aicourse.controller;

import cn.edu.szu.aicourse.common.result.Result;
import cn.edu.szu.aicourse.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Tag(name = "File 模块")
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "上传File")
    public Result<String> uploadFile(@RequestPart("file") MultipartFile file) {
        String fileName = fileService.saveFile(file);
        return Result.success(fileName, "文件上传成功");
    }

    @DeleteMapping("/{fileName}")
    @Operation(summary = "删除File")
    public Result<String> deleteFile(@PathVariable String fileName) {
        fileService.deleteFile(fileName);
        return Result.success("文件删除成功");
    }

    @GetMapping("/list")
    @Operation(summary = "获取File列表")
    public Result<List<String>> getAllFiles() {
        List<String> files = fileService.getAllFiles();
        return Result.success(files, "获取文件列表成功");
    }
}
