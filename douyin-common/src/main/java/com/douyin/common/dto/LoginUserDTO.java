package com.douyin.common.dto;

import lombok.Data;

/**
 * Author:WJ
 * Date:2023/2/5 15:15
 * Description:<>
 */
@Data
public class LoginUserDTO {
    /**
     *用户id
     */
    private Long id;
    /**
     * 用户名称
     */
    private String username;
}
