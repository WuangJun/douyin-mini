package com.douyin.entity;

import lombok.Builder;
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

    private String password;

    private Date createdTime;

    private Date modifiedTime;

    private Integer version; //版本号，用于乐观锁处理

    private Integer isDeleted; //逻辑删除
}
