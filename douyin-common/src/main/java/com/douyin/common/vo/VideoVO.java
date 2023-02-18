package com.douyin.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author:WJ
 * Date:2023/2/18 14:20
 * Description:<>
 */
@Data
public class VideoVO implements Serializable {

    private Long id;

    private UserVO user;

    private String play_url; //视频地址

    private String cover_url; //封面地址

    private Integer favorite_count;

    private Integer comment_count;

    private boolean is_favorite;

    private String title; //视频标题
}
