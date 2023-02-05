package com.douyin.common.vo;

import lombok.Builder;
import lombok.Data;

/**
 * Author:WJ
 * Date:2023/2/5 17:10
 * Description:<>
 */

@Data
public class BaseResponseVO {
    /**
     * 状态码，0是成功 其他值是失败
     */
    private Integer status_code;
    /**
     * 状态描述
     */
    private String status_msg;

    public BaseResponseVO(){
    }

    public BaseResponseVO(Integer status_code, String msg) {
        this.status_code=status_code;
        this.status_msg=msg;
    }


    public static BaseResponseVO fail(String msg) {
        return new BaseResponseVO(-1, msg);
    }

}
