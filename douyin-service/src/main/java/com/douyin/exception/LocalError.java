package com.douyin.exception;

/**
 * @author caiyihua
 */
public interface LocalError {
    /**
     * 获取错误码
     *
     * @return
     */
    Integer getCode();

    /**
     * 获取错误信息
     *
     * @return
     */
    String getMessage();
    
}
