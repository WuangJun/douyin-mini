package com.douyin.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:WJ
 * Date:2023/2/18 14:38
 * Description:<输出到前端的用户信息>
 */
@Data
public class UserVO implements Serializable {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    private String name;

    private int follow_count;

    private int follower_count;


    private boolean is_follow;
    @JsonProperty(value = "is_follow")
    public boolean isIs_follow() {
        return is_follow;
    }
}
