package com.douyin.exception;

import com.douyin.common.contants.Constant;
import lombok.extern.slf4j.Slf4j;

/**
 * Author:WJ
 * Date:2023/2/1 12:42
 * Description:<>
 */
@Slf4j
public class CommonException extends RuntimeException implements LocalError {

    /**
     * 状态码，0是成功 其他值是失败
     */
    private  Integer status_code;
    /**
     * 状态描述
     */
    private  String status_msg;

    private  Object[] params;


    public CommonException(String message) {
        super();
        this.status_code = Constant.FAIL_CODE;
        this.status_msg = message;
        this.params = null;
    }

    public CommonException(Integer code, String message) {
        super();
        this.status_code = code;
        this.status_msg = message;
        this.params = null;
    }

    public CommonException(LocalError localError) {
        this(localError.getCode(), localError.getMessage());
    }

    public CommonException(Integer code, String message, Object... params) {
        super();
        this.status_code = code;
        this.status_msg = message;
        this.params = params;
    }

    public CommonException(LocalError localError, Object... params) {
        this(localError.getCode(), localError.getMessage(), params);
    }

    public Integer getCode() {
        return status_code;
    }

    @Override
    public String getMessage() {
        if (params != null && params.length > 0) {
            return String.format(status_msg, params);
        }
        return status_msg;
    }
}
