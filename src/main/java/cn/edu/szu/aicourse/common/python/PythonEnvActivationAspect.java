package cn.edu.szu.aicourse.common.python;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PythonEnvActivationAspect {

    @Before("@annotation(ActivatePythonEnv)")
    public void beforeMethodExecution() {
        // 在这里激活Python环境
        log.info("Activating Python environment before method execution.");
        // 例如，设置环境变量，启动Python进程等

    }
}
