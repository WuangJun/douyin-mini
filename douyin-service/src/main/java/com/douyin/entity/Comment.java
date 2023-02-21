package com.douyin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author:WJ
 * Date:2023/2/1 12:29
 * Description:<>
 */

@Data
@TableName("dy_video_comment")
public class Comment implements Serializable {

    private Long id;

    private Long commentUserId;

    private Long videoId;

    private String commentText;

    private Date createdTime;

    private Date modifiedTime;

    private Integer version; //版本号，用于乐观锁处理

    private Integer isDeleted; //逻辑删除
}
