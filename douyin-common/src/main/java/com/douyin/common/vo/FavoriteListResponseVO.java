package com.douyin.common.vo;

import com.douyin.common.constants.StateConstant;
import lombok.Data;

import java.util.List;

/**
 * FavoriteListResponseVO
 *
 * @Author: jiahz
 * @Date: 2023/2/16 17:08
 * @Description:
 */
@Data
public class FavoriteListResponseVO extends BaseResponseVO{

    private List<VideoVO> video_list;


    public FavoriteListResponseVO(Integer status_code, String status_msg) {
        super(status_code, status_msg);
    }

    public FavoriteListResponseVO(Integer status_code, String status_msg, List<VideoVO> video_list) {
        super(status_code, status_msg);
        this.video_list = video_list;
    }

    public static FavoriteListResponseVO success(List<VideoVO> videoList) {
        return new FavoriteListResponseVO(StateConstant.SUCCESS_CODE, "成功", videoList);
    }
}
