package cn.edu.szu.aicourse.service;



public interface GraphragService {
    // 上传文件
    void uploadTxtFile();
    // 调用GraphRAG
    void invokeGraphRAG();
    // 将结果转存Neo4j
    void restoreResultToNeo4j();
    // 查询GraphRAG
    String queryGraphRAG(String query);
}
