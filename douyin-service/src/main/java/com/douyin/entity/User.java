package com.douyin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author:WJ
 * Date:2023/2/1 12:09
 * Description:<>
 */
@Data
public class User implements Serializable {

    private Long id;

    private String username;

    private Long followCount; //关注总数

    private Long followerCount; //粉丝总数

    private Long totalFavorite; //获赞总数

    private Long workCount; //作品总数

    private Long favoriteCount; //点赞总数

    private String password;

    private Date createdTime;

    private Date modifiedTime;

    private Integer version; //版本号，用于乐观锁处理

    private Integer isDeleted; //逻辑删除
}
