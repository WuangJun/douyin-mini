package com.douyin.common.vo;

import com.douyin.common.dto.UserDTO;
import lombok.Data;

/**
 * Author:WJ
 * Date:2023/2/1 23:54
 * Description:<查询用户信息的返回对象>
 */

@Data

public class UserInfoResponseVO extends BaseResponseVO {


    // TODO: 2023/2/5 WJ user中少了关注相关参数，需验证客户端能否正确渲染
    private UserVO user;

    public UserInfoResponseVO(Integer status_code, String status_msg, UserVO userVO) {
        super(status_code, status_msg);
        this.user = userVO;
    }

    public static UserInfoResponseVO success(UserVO userVo) {
        return new UserInfoResponseVO(0, "成功", userVo);
    }

}
