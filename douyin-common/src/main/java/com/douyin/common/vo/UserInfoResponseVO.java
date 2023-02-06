package com.douyin.common.vo;

import com.douyin.common.dto.UserDTO;
import lombok.Data;

/**
 * Author:WJ
 * Date:2023/2/1 23:54
 * Description:<>
 */


@Data

public class UserInfoResponseVO extends BaseResponseVO {


    // TODO: 2023/2/5 WJ user中少了关注相关参数，需验证客户端能否正确渲染
    private UserDTO user;

    public UserInfoResponseVO(Integer status_code, String status_msg, UserDTO userDTO) {
        super(status_code, status_msg);
        this.user = userDTO;
    }

    public static UserInfoResponseVO success(UserDTO userDto) {
        return new UserInfoResponseVO(0, "成功", userDto);
    }

}
