package cn.edu.szu.aicourse.controller;


import cn.edu.szu.aicourse.common.Result;
import cn.edu.szu.aicourse.service.GraphragService;
import cn.edu.szu.aicourse.service.MinioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/graphrag")
@AllArgsConstructor
public class GraphragController {

    private final GraphragService graphragService;
    private final MinioService minioService;
    @PostMapping("/upload")
    public Result uploadTxtFile(@RequestBody MultipartFile file) {
        // TODO: 完成上传文件
        String url = minioService.upload(file);
        return Result.success(url, "upload file successfully");
    }
    @GetMapping("/invoke")
    public Result invokeGraphRAG() {
        // TODO: 调用GraphRAG
        graphragService.invokeGraphRAG();
        graphragService.restoreResultToNeo4j();

        return Result.success("invoke graphrag successfully");
    }

    @GetMapping("/query")
    public Result queryGraphRAG(@RequestParam String query) {
        // TODO: 对RAG做查询
        String queryResult = graphragService.queryGraphRAG(query);
        return Result.success(queryResult);
    }
}
