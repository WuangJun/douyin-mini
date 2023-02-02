package com.douyin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author:WJ
 * Date:2023/2/1 12:21
 * Description:<>
 */
@Data
public class Video implements Serializable {

    private Long id;

    private Long authorId;

    private String title;

    private String playUrl; //视频地址

    private String coverUrl; //封面地址

    private Integer favoriteCount;

    private Integer commentCount;

    private Date createdTime;

    private Date modifiedTime;

    private Integer version; //版本号，用于乐观锁处理

    private Integer isDeleted; //逻辑删除
}
