package cn.edu.szu.aicourse.service;


import org.neo4j.driver.*;


public interface Neo4jClientService {
     void run(String cypher);
     // 用于执行读或写cypher语句
}
