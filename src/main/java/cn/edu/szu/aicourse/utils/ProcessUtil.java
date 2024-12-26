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
    private static final int defaultTimeout = 3600;
    public static String run(String[] cmd) {
        return run(cmd, null, defaultTimeout);
    }
    public static String run(String[] cmd, String directory) {
        return run(cmd, directory, defaultTimeout);
    }
    public static String run(String[] cmd, String directory, int timeout) {
        if (cmd.length == 0 ) return "empty command";
        StringBuilder sb = new StringBuilder();
        try {
            // 创建ProcessBuilder对象
            ProcessBuilder processBuilder = new ProcessBuilder();
            // 如果是python命令，需指定IO编码
            if (cmd[0].contains("python"))
                processBuilder.environment().put("PYTHONIOENCODING", "utf-8");
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
                        sb.append(line + '\n');
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
        return sb.toString();
    }
}
