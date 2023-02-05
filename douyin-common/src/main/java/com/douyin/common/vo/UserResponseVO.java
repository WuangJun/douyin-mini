package com.douyin.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.douyin.common.contants.Constant;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * Author:WJ
 * Date:2023/2/1 23:30
 * Description:<>
 */

@Data
public class UserResponseVO extends BaseResponseVO{

    /**
     * 用户id
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long user_id;
    /**
     * 用户鉴权token
     */
    private String token;

    public UserResponseVO(Integer status_code, String status_msg){
        super(status_code,status_msg);
    }
    public UserResponseVO(Integer status_code, String status_msg, Long userId,String token){
        super(status_code,status_msg);
        this.user_id=userId;
        this.token=token;
    }

    public static UserResponseVO success(Long userId, String token){
        return new UserResponseVO(Constant.SUCCESS_CODE,"成功",userId,token);
    }

    public static UserResponseVO fail(String msg) {
        return new UserResponseVO(Constant.FAIL_CODE,msg);
    }
}


