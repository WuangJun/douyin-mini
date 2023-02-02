package com.douyin.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * Author:WJ
 * Date:2023/2/1 23:53
 * Description:<>
 */
public class UserDto {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    private String name;
    // private Integer follow_count;
    // private Integer follower_count;
    // private Boolean is_follow;
}
