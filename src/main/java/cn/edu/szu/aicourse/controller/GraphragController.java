package cn.edu.szu.aicourse.controller;



import cn.edu.szu.aicourse.common.result.Result;
import cn.edu.szu.aicourse.service.GraphragService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graphrag")
@RequiredArgsConstructor
@Tag(name = "graph 模块")
public class GraphragController {

    private final GraphragService graphragService;

    @GetMapping("/invoke")
    @Operation(summary = "嵌入文件")
    public Result<String> invokeGraphRAG() {
        // TODO: 调用GraphRAG
        graphragService.invokeGraphRAG();
        graphragService.restoreResultToNeo4j();

        return Result.success("invoke graphrag successfully");
    }

    @GetMapping("/query")
    @Operation(summary = "查询")
    public Result<String> queryGraphRAG(@RequestParam String query) {
        // TODO: 对RAG做查询
        String queryResult = graphragService.queryGraphRAG(query);
        return Result.success(queryResult);
    }
}
