package cn.edu.szu.aicourse.service;



public interface GraphragService {
    // 调用GraphRAG
    void invokeGraphRAG();
    // 将结果转存Neo4j
    void restoreResultToNeo4j();

    String queryGraphRAG(String query);
}
