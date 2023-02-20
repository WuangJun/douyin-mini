package com.douyin.common.vo;

import com.douyin.common.constants.StateConstant;
import lombok.Data;

/**
 * FavoriteResponseVO
 *
 * @Author: jiahz
 * @Date: 2023/2/16 15:26
 * @Description:
 */
@Data
public class FavoriteResponseVO extends BaseResponseVO {

    public FavoriteResponseVO(Integer status_code, String status_msg) {
        super(status_code, status_msg);
    }

    public static FavoriteResponseVO success() {
        return new FavoriteResponseVO(StateConstant.SUCCESS_CODE, "成功");
    }

}
