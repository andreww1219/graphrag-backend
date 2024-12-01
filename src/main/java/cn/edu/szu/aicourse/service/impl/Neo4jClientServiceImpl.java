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

    /**
     * 适用执行写逻辑
     * @param cypher cypher
     * @return Result 由于return之前session已经被关闭，该result不能被消费
     * @throws Neo4jException 执行cypher出现异常
     */
    public Result run(String cypher) throws Neo4jException {
        Session session = driver.session();
        Transaction ts = session.beginTransaction();
        try {
            Result result = ts.run(cypher);
            ts.commit();
            return result;
        } catch (Exception e) {
            ts.rollback();
            log.error("run neo4j cypher error with ", e);
            throw new Neo4jException("run " + cypher + " error with" + e.getMessage());
        }finally {
            ts.close();
            session.close();
        }
    }

    /**
     * 用于执行读或写cypher语句
     * @param gql cypher
     * @return Result
     * @throws Neo4jException 执行cypher出现异常
     */
    public Result exec(String gql) throws Neo4jException {
        try {
            Session session = driver.session();
            log.info("exec {}", gql);
            return session.run(gql);
        } catch (Exception e) {
            log.error("execute gql {} error ", gql, e);
            throw new Neo4jException("execute " + gql + " error with" + e.getMessage());
        }
    }
}
