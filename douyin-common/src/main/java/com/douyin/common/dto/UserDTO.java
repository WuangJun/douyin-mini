package com.douyin.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:WJ
 * Date:2023/2/1 23:53
 * Description:<存储用户信息>
 */
@Data
public class UserDTO implements Serializable {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    private String username;
    private String password;

}
