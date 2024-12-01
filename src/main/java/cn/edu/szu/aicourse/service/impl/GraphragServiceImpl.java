package cn.edu.szu.aicourse.service.impl;

import cn.edu.szu.aicourse.common.python.ActivatePythonEnv;
import cn.edu.szu.aicourse.service.GraphragService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
@Slf4j
public class GraphragServiceImpl implements GraphragService {

    @Value("${graphrag.root}")
    private String graphragRoot;

    @Value("${neo4j.root}")
    private String neo4jRoot;

    private final String[] buildCmd = {"python", "-m", "graphrag.index", "--root", "."};

    private String getLatestOutputFolder() throws FileNotFoundException {
        // 获取最晚输出的文件夹
        File dir = new File(graphragRoot, "output");
        File[] folders = dir.listFiles(File::isDirectory);
        if (folders == null)
            throw new FileNotFoundException("未找到输出文件夹");
        File maxAlphaFolder = null;
        for (File folder : folders) {
            if (maxAlphaFolder == null
                    || folder.getName().compareTo(maxAlphaFolder.getName()) > 0)
                maxAlphaFolder = folder;
        }
        if (maxAlphaFolder == null)
            throw new FileNotFoundException("输出文件夹下不存在文件夹");
        log.info("字典序最大的文件夹是: " + maxAlphaFolder.getAbsolutePath());
        return maxAlphaFolder.getAbsolutePath();
    }
    @Override
    @ActivatePythonEnv
    public void invokeGraphRAG() {
        // 调用GraphRAG
        try {
            // 创建ProcessBuilder对象
            ProcessBuilder processBuilder = new ProcessBuilder();
            // 设置工作目录
            processBuilder.directory(new File(graphragRoot));
            // 设置要执行的命令
            processBuilder.command(buildCmd);
            // 启动进程
            Process process = processBuilder.start();
            // 等待进程结束
            int exitCode = process.waitFor();
            log.info("Process exit code: " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将输出的 parquet 转换为 csv
        try {
            String latestOutputFolder = getLatestOutputFolder();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restoreResultToNeo4j() {
        // TODO: 将结果转存Neo4j
    }

    @Override
    public String queryGraphRAG(String query) {
        // TODO: 对RAG做查询
        return null;
    }
}
