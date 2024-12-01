package cn.edu.szu.aicourse.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "neo4j")
public class Neo4jClientConfig {
    private String uri;
    private String username;
    private String password;
}
