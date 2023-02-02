package com.douyin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author:WJ
 * Date:2023/2/1 12:33
 * Description:<>
 */

@Data
public class Favorite implements Serializable {

    private Long id;

    private Long favoriteUserId;

    private Long videoId;

    private Date createdTime;

    private Date modifiedTime;

    private Integer version; //版本号，用于乐观锁处理

    private Integer isDeleted; //逻辑删除
}
