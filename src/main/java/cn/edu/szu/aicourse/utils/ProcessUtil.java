package cn.edu.szu.aicourse.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Slf4j
public class ProcessUtil {
    public static void run(String[] cmd) {
        run(cmd, null, 60);
    }
    public static void run(String[] cmd, String directory, int timeout) {
        try {
            // 创建ProcessBuilder对象
            ProcessBuilder processBuilder = new ProcessBuilder();
            Map<String, String> env = processBuilder.environment();
            env.put("PYTHONIOENCODING", "utf-8");
            // 设置工作目录
            if (directory != null) processBuilder.directory(new File(directory));
            // 设置要执行的命令
            log.info("begin to start command: {} on directory {}", cmd, directory);
            processBuilder.command(cmd);
            // 启动进程
            Process process = processBuilder.start();

            // 另外两线程输出日志
            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.submit(() -> {
                try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = stdInput.readLine()) != null) {
                        log.info(line);
                    }
                } catch (Exception e) {
                    log.error("Error reading standard output", e);
                }
            });
            executor.submit(() -> {
                try (BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = stdError.readLine()) != null) {
                        log.info("Error: " + line);
                    }
                } catch (Exception e) {
                    log.error("Error reading standard error", e);
                }
            });

            boolean finished = process.waitFor(timeout, TimeUnit.SECONDS);
            if (finished) {
                log.info("Process of {} finished.", cmd);
            } else {
                log.info("Process of {} timed out, terminating...", cmd);
                process.destroy(); // 可以调用destroyForcibly()来强制终止进程
            }
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
