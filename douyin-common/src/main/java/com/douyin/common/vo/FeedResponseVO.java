package com.douyin.common.vo;

import com.douyin.common.constants.StateConstant;
import lombok.Data;

import java.util.List;

/**
 * Author:WJ
 * Date:2023/2/18 14:27
 * Description:<返回的视频流信息>
 */
@Data
public class FeedResponseVO extends BaseResponseVO {

    private List<VideoVO> video_list;

    /**
     * 本次返回的视频中，发布最早的时间，作为下次请求的lastTime
     */
    private Long next_time;

    public FeedResponseVO(Integer status_code, String status_msg, List<VideoVO> videoList, Long next_time) {
        super(status_code, status_msg);
        this.video_list = videoList;
        this.next_time = next_time;
    }

    public static FeedResponseVO success(List<VideoVO> videoList, Long next_time) {
        return new FeedResponseVO(StateConstant.SUCCESS_CODE, "成功", videoList, next_time);
    }
}
