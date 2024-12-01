package cn.edu.szu.aicourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.File;

@SpringBootApplication
@EnableConfigurationProperties
public class GraphragBackendApplication {

	public static void main(String[] args) {
		// 确保上传目录存在
		String uploadPath = System.getProperty("user.dir") + "/uploads";
		new File(uploadPath).mkdirs();
		
		SpringApplication.run(GraphragBackendApplication.class, args);
	}

}
