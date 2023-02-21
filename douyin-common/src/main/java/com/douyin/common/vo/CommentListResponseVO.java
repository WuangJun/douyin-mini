package com.douyin.common.vo;

import com.douyin.common.constants.StateConstant;
import lombok.Data;

import javax.xml.stream.events.Comment;
import java.util.List;

/**
 * CommentListResponseVO
 *
 * @author chengyu
 */
@Data
public class CommentListResponseVO extends BaseResponseVO {
    private List<CommentVO> comment_list;


    public CommentListResponseVO(Integer status_code, String status_msg, List<CommentVO> comment_list) {
        super(status_code, status_msg);
        this.comment_list = comment_list;
    }

    public static CommentListResponseVO success(List<CommentVO> commentVOList) {
        return new CommentListResponseVO(StateConstant.SUCCESS_CODE, "成功", commentVOList);
    }
}
