package com.douyin.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;

/**
 * Author:WJ
 * Date:2023/2/1 23:30
 * Description:<>
 */
@Builder
@Data
public class UserResponseVo {
    /**
     * 状态码，0是成功 其他值是失败
     */
    private Integer status_code;
    /**
     * 状态描述
     */
    private String status_msg;
    /**
     * 用户id
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long user_id;
    /**
     * 用户鉴权token
     */
    private String token;

    public static UserResponseVo success(Long userId,String token){
        return new UserResponseVo(0, "成功", userId,token);
    }
    public static UserResponseVo fail(String msg){
        return new UserResponseVo(-1, msg,null,null);
    }

}
