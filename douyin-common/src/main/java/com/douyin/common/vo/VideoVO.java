package com.douyin.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:WJ
 * Date:2023/2/18 14:20
 * Description:<输出到前端的视频信息>
 */
@Data
public class VideoVO implements Serializable {

    private Long id;

    private UserVO author;

    private String play_url; // 视频地址

    private String cover_url; // 封面地址

    private Integer favorite_count;

    private Integer comment_count;
    private boolean is_favorite;

    private String title; // 视频标题

    @JsonProperty(value = "is_favorite")
    public boolean isIs_favorite() {
        return is_favorite;
    }
}
