package cn.edu.szu.aicourse.service;


import org.neo4j.driver.*;


public interface Neo4jClientService {
     // 适用执行写逻辑
     Result run(String cypher);
     // 用于执行读或写cypher语句
     Result exec(String gql);
}
