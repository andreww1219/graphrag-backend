package cn.edu.szu.aicourse.service.impl;

import cn.edu.szu.aicourse.service.GraphragService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GraphragServiceImpl implements GraphragService {
    
    @Value("${graphrag.root}")
    private String graphragRoot;

    @Value("${neo4j.root}")
    private String neo4jRoot;

    @Override
    public void invokeGraphRAG() {
        // TODO: 调用GraphRAG
    }

    @Override
    public void restoreResultToNeo4j() {
        // TODO: 将结果转存Neo4j
    }

    @Override
    public String queryGraphRAG(String query) {
        // TODO: 对RAG做查询
        return null;
    }
}
