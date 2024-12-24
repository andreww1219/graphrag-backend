package cn.edu.szu.aicourse.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author BlankXiao
 * @description SwaggerConfig
 * @date 2024/12/24 0:17
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API文档")
                        .version("1.0")
                        .description("API接口文档")
                        .contact(new Contact()
                                .name("BlankXiao")
                                .email("your.email@example.com")));
    }
}
