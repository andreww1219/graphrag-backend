package cn.edu.szu.aicourse.common.result;

public interface ResponseCodeConstants {
    /**
     * 接口成功
     */
    int SUCCESS = 200;
    /**
     * 接口失败 发生异常
     */
    int FAIL = 500;
    /**
     * 客户端异常
     */
    int BAD_REQUEST = 400;

    /**
     * 找不到该请求
     */
    int NOT_FOUND = 404;
}
