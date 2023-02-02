package com.douyin.common.vo;

import com.douyin.common.dto.UserDto;
import lombok.Builder;
import lombok.Data;

/**
 * Author:WJ
 * Date:2023/2/1 23:54
 * Description:<>
 */

@Builder
@Data
public class UserInfoResponseVo {

    /**
     * 状态码，0是成功 其他值是失败
     */
    private Integer status_code;
    /**
     * 状态描述
     */
    private String status_msg;

    private UserDto userDto;

    public static UserInfoResponseVo suceess(UserDto userDto){
        return new UserInfoResponseVo(0,"成功",userDto);
    }

    public static UserInfoResponseVo fail(){
        return new UserInfoResponseVo(-1, "失败",null);

    }
}
