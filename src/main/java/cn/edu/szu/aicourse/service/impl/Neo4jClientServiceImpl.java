package cn.edu.szu.aicourse.service.impl;


import cn.edu.szu.aicourse.config.Neo4jClientConfig;
import cn.edu.szu.aicourse.service.Neo4jClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.*;
import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Neo4jClientServiceImpl implements Neo4jClientService {

    private final Driver driver;

    public Neo4jClientServiceImpl(Neo4jClientConfig neo4jConfig) {
        this.driver = GraphDatabase.driver(neo4jConfig.getUri(), AuthTokens.basic(neo4jConfig.getUsername(), neo4jConfig.getPassword()));
    }
    public void run(String cypher) throws Neo4jException {
        Session session = null;
        Transaction tx = null;
        try {
            session = driver.session(); // 开启一个新的会话
            tx = session.beginTransaction(); // 开启一个新的事务

            // 分割Cypher语句，确保每个语句以分号结束
            String[] queries = cypher.split(";");
            log.info("ready to execute {} queries", queries.length);
            for (String query : queries) {
                // 跳过空字符串，这可能会在分割时出现
                if (!query.trim().isEmpty()) {
                    tx.run(query.trim()); // 执行每个Cypher语句
                }
//                log.info(query);
            }
            // 提交事务
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback(); // 如果发生异常，回滚事务
            }
            log.error("run neo4j cypher error with ", e);
            throw new Neo4jException("run " + cypher + " error with " + e.getMessage());
        } finally {
            if (session != null) {
                session.close(); // 关闭会话
            }
        }
    }
}
