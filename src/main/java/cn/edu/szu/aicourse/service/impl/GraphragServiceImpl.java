package cn.edu.szu.aicourse.service.impl;

import cn.edu.szu.aicourse.service.GraphragService;
import cn.edu.szu.aicourse.service.Neo4jClientService;
import cn.edu.szu.aicourse.utils.ProcessUtil;
import cn.hutool.core.io.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class GraphragServiceImpl implements GraphragService {

    @Value("${graphrag.root}")
    private String graphragRoot;

    @Value("${neo4j.root}")
    private String neo4jRoot;
    @Value("${python-venv.interpreter}")
    private String pythonInterpreter;

    private final Neo4jClientService neo4jClientService;

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
    public void invokeGraphRAG() {
        // 调用GraphRAG
        String[] buildCmd = {pythonInterpreter, "-m", "graphrag.index", "--root", graphragRoot};
        ProcessUtil.run(buildCmd);
    }

    @Override
    public void restoreResultToNeo4j() {
        // 将结果转存Neo4j
        // 将输出的 parquet 转换为 csv
        try {
            String latestOutputFolder = getLatestOutputFolder();
            String parquetDir = Paths.get(latestOutputFolder, "artifacts").toString();
            String csvDir = Paths.get(neo4jRoot, "import").toString();
            // 清空文件夹下的文件
            FileUtil.clean(csvDir);

            // 从resources目录中获取Python脚本
            String pythonScriptPath = new ClassPathResource("scripts/parquet2csv.py")
                                            .getFile().getAbsolutePath();
            String[] cmd = {pythonInterpreter, pythonScriptPath, parquetDir, csvDir};
            ProcessUtil.run(cmd);

            // 运行cypher语句加载import目录下的csv
            File loadCypher = new ClassPathResource("cypher/load.cypher").getFile();
            String cypher = FileUtil.readUtf8String(loadCypher);
            neo4jClientService.run(cypher);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String queryGraphRAG(String query) {
        // TODO: 对RAG做查询
        return null;
    }
}
