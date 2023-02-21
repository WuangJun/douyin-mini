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

    @JsonProperty("name")
    private String username;

    @JsonProperty("follow_count")
    private Long followCount; //关注总数

    @JsonProperty("follower_count")
    private Long followerCount; //粉丝总数

    @JsonProperty("total_favorited")
    private Long totalFavorite; //获赞总数

    @JsonProperty("work_count")
    private Long workCount; //作品总数

    @JsonProperty("favorite_count")
    private Long favoriteCount; //点赞总数

    @JsonProperty("is_follow")
    private boolean is_follow;

}
