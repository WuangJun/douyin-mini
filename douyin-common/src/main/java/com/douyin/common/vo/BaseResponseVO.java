package com.douyin.common.vo;

import com.douyin.common.constants.StateConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:WJ
 * Date:2023/2/5 17:10
 * Description:<返回到前端的基础接口>
 */

@Data
public class BaseResponseVO implements Serializable {
    /**
     * 状态码，0是成功 其他值是失败
     */
    private Integer status_code;
    /**
     * 状态描述
     */
    private String status_msg;

    public BaseResponseVO() {
    }

    public BaseResponseVO(Integer status_code, String msg) {
        this.status_code = status_code;
        this.status_msg = msg;
    }

    public static BaseResponseVO success() {
        return new BaseResponseVO(StateConstant.SUCCESS_CODE, "成功");
    }
}
