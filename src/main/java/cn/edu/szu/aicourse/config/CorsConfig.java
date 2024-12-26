package cn.edu.szu.aicourse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author BlankXiao
 * @description Corsconfig
 * @date 2024/12/24 10:37
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")  // 允许的前端域名
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的HTTP方法
                .allowedHeaders("*")  // 允许的请求头
                .allowCredentials(true)  // 允许携带cookie
                .maxAge(3600);  // 预检请求的有效期，单位为秒
    }
}
