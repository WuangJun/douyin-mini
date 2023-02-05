package com.douyin.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Author:WJ
 * Date:2023/2/1 12:42
 * Description:<>
 */
@Slf4j
public class CommonException extends RuntimeException{

    /**
     * 状态码，0是成功 其他值是失败
     */
    private Integer status_code;
    /**
     * 状态描述
     */
    private String status_msg;
    public CommonException(String msg){

        super(msg);
    }
}
