package cn.edu.szu.aicourse.handler;

import cn.edu.szu.aicourse.common.ResponseCodeConstants;
import cn.edu.szu.aicourse.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> handleIllegalArgumentException(IllegalArgumentException e){
        log.warn("参数错误: {}", e.getMessage());
        return Result.failed(ResponseCodeConstants.BAD_REQUEST, e.getMessage());
    }

    public Result<?> handleException(Exception e){
        log.error("系统异常", e);
        return Result.failed("系统异常");
    }
}
