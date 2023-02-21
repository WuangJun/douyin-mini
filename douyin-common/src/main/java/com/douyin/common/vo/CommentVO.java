package com.douyin.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用于输出到前端的评论
 *
 * @author chengyu
 */

@Data
public class CommentVO implements Serializable{

    private Long id;

    private UserVO user;

    @JsonProperty("content")
    private String commentText;

    private String create_date;

}
