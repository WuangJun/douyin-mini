package com.douyin.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:WJ
 * Date:2023/2/18 14:20
 * Description:<输出到前端的视频信息>
 */
@Data
public class VideoVO implements Serializable {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    private UserVO author;

    @JsonProperty(value = "play_url")
    private String playUrl; //视频地址

    @JsonProperty(value = "cover_url")
    private String coverUrl; //封面地址

    @JsonProperty(value = "favorite_count")
    private Integer favoriteCount; //获赞总数

    @JsonProperty(value = "comment_count")
    private Integer commentCount; //评论总数

    @JsonProperty(value = "is_favorite")
    private boolean is_favorite;

    private String title; // 视频标题

}
