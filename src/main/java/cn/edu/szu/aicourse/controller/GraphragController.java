package cn.edu.szu.aicourse.controller;



import cn.edu.szu.aicourse.common.result.Result;
import cn.edu.szu.aicourse.service.GraphragService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graphrag")
@RequiredArgsConstructor
public class GraphragController {

    private final GraphragService graphragService;

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
