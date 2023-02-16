package com.douyin.common.vo;

import com.douyin.common.contants.Constant;
import com.douyin.common.dto.VideoDTO;
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

    private List<VideoDTO> videoList;


    public FavoriteListResponseVO(Integer status_code, String status_msg) {
        super(status_code, status_msg);
    }

    public FavoriteListResponseVO(Integer status_code, String status_msg, List<VideoDTO> videoList) {
        super(status_code, status_msg);
        this.videoList = videoList;
    }

    public static FavoriteListResponseVO success(List<VideoDTO> videoList) {
        return new FavoriteListResponseVO(Constant.SUCCESS_CODE, "成功", videoList);
    }
}
