package com.douyin.common.dto;

import lombok.Data;

/**
 * AuthorDTO
 *
 * @Author: jiahz
 * @Date: 2023/2/16 17:19
 * @Description:
 */
@Data
public class AuthorDTO {

    private Long id;

    private String name;

    private Integer followCount;

    private Integer followerCount;

    private Boolean isFollow;

}
